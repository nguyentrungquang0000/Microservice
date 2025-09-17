package com.example.Order_service.order;

import com.example.Order_service.Address.AddressClient;
import com.example.Order_service.Address.AddressDTO;
import com.example.Order_service.ApiResponse;
import com.example.Order_service.ProductOption.ProductOptionClient;
import com.example.Order_service.ProductOption.ProductOptionDTO;
import com.example.Order_service.ProductOption.ProductOptionResponse;
import com.example.Order_service.cartItem.CartItem;
import com.example.Order_service.cartItem.CartItemRepository;
import com.example.Order_service.cartItem.CartItemService;
import com.example.Order_service.kafka.OrderConfirmation;
import com.example.Order_service.kafka.OrderProducer;
import com.example.Order_service.oderitem.OrderItem;
import com.example.Order_service.oderitem.OrderItemRepository;
import com.example.Order_service.order.modal.enumm.OrderStatus;
import com.example.Order_service.order.modal.request.AddOrderRequest;
import com.example.Order_service.order.modal.request.PageableRequest;
import com.example.Order_service.order.modal.response.OrderDTO;
import com.example.Order_service.order.modal.response.OrderItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductOptionClient productOptionClient;
    private final CartItemRepository cartItemRepository;
    private final CartItemService cartItemService;
    private final AddressClient addressClient;
    private final OrderItemRepository orderItemRepository;
    private final OrderProducer orderProducer;

    @Transactional
    public ResponseEntity<ApiResponse<?>> addOrder(AddOrderRequest request, String userId) {
        OrderConfirmation orderConfirmation = new OrderConfirmation();
        List<ProductOptionResponse> productOptionResponseList = new ArrayList<>();
        Orders order = new Orders();
        order.setCustomerId(userId);
        order.setMethod(request.getMethod());
        AddressDTO addressDTO = addressClient.getAddress(request.getAddressId());
        order.setName(addressDTO.getName());
        order.setCity(addressDTO.getCity());
        order.setWard(addressDTO.getWard());
        order.setAddress(addressDTO.getAddress());
        order.setPhone(addressDTO.getPhone());
        order.setDescription(request.getDescription());
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.WAITING_CONFIRMATION);
        BigDecimal amout = new BigDecimal("0.00");

        for (String cartItemId : request.getCartItemIds()) {
            CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow();
            ProductOptionDTO productOptionDTO = productOptionClient.getProductOption(cartItem.getProductOptionId());
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setProductOptionId(cartItem.getProductOptionId());
            BigDecimal price = productOptionDTO.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())).multiply(BigDecimal.valueOf(100 - productOptionDTO.getDiscount())
                    .divide(BigDecimal.valueOf(100), MathContext.DECIMAL128));
            orderItem.setPrice(price);
            orderItemRepository.save(orderItem);
            cartItemService.deleteCatItem(cartItemId);
            amout = amout.add(price);

            //list reponse
            ProductOptionResponse productOptionResponse = new ProductOptionResponse();
            productOptionResponse.setProductId(productOptionDTO.getProductId());
            productOptionResponse.setProductName(productOptionDTO.getName());
            productOptionResponse.setProductOptionName(productOptionDTO.getProductName());
            productOptionResponse.setPrice(price);
            productOptionResponse.setQuantity(cartItem.getQuantity());
            productOptionResponseList.add(productOptionResponse);
        }

        order.setAmount(amout);
        orderConfirmation.setAmount(amout);
        orderConfirmation.setAddress(addressDTO);
        orderConfirmation.setPaymentMethod(request.getMethod());
        orderConfirmation.setProductOptions(productOptionResponseList);

        String orderId = orderRepository.save(order).getId();
        orderConfirmation.setOrderId(orderId);
        orderProducer.sendOrderConfirmation(orderConfirmation);

        return ResponseEntity.ok(new ApiResponse<>(200, "ok", orderId));
    }

    public ResponseEntity<ApiResponse<?>> getOrderById(String orderId) {
        Orders order = orderRepository.findById(orderId).orElseThrow();
        List<OrderItem> orderItems = order.getOrderItems();

        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();

        for(OrderItem orderItem : orderItems) {
            ProductOptionDTO productOptionDTO = productOptionClient.getProductOption(orderItem.getProductOptionId());
            OrderItemDTO orderItemDTO = OrderItemDTO.builder()
                    .id(orderItem.getId())
                    .price(orderItem.getPrice())
                    .quantity(orderItem.getQuantity())
                    .productOption(productOptionDTO)
                    .build();
            orderItemDTOList.add(orderItemDTO);
        }
        OrderDTO orderDTO = OrderDTO.builder()
                .id(order.getId())
                .name(order.getName())
                .phone(order.getPhone())
                .createdAt(order.getCreatedAt())
                .description(order.getDescription())
                .amount(order.getAmount())
                .status(order.getStatus())
                .address(order.getAddress() + " - " + order.getWard() + " - " + order.getCity())
                .orderItemDTOS(orderItemDTOList)
                .build();
        return ResponseEntity.ok(new ApiResponse<>(200, "ok", orderDTO));
    }

    public ResponseEntity<ApiResponse<?>> getAllOrders(String userId, PageableRequest pageableRequest) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(pageableRequest.getPage(), pageableRequest.getLimit(), sort);
        Page<Orders> ordersList ;
        if(pageableRequest.getStatus().equals("ALL")){
            ordersList = orderRepository.findByCustomerId(userId, pageable);
        }else{
            ordersList = orderRepository.findByCustomerIdAndStatus(userId,OrderStatus.valueOf(pageableRequest.getStatus()), pageable);
        }

        List<OrderDTO> orderDTOList = new ArrayList<>();
        for(Orders orders : ordersList.getContent()) {
            OrderDTO orderDTO = OrderDTO.builder()
                    .id(orders.getId())
                    .createdAt(orders.getCreatedAt())
                    .status(orders.getStatus())
                    .amount(orders.getAmount())
                    .build();
            orderDTOList.add(orderDTO);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("content", orderDTOList);
        response.put("page", ordersList.getNumber());
        response.put("limit", ordersList.getSize());
        response.put("totalPages", ordersList.getTotalPages());
        response.put("totalElements", ordersList.getTotalElements());
        response.put("isFirst", ordersList.isFirst());
        response.put("isLast", ordersList.isLast());
        return ResponseEntity.ok(new ApiResponse<>(200, "ok", response));
    }

    public ResponseEntity<ApiResponse<?>> putStatusOrder(String orderId, String status) {

        Orders order = orderRepository.findById(orderId).orElseThrow();
        order.setStatus(OrderStatus.valueOf(status));
//        order.setDeliveredAt(LocalDateTime.now());
        orderRepository.save(order);
        return ResponseEntity.ok(new ApiResponse<>(200, "ok", orderId));
    }

    public ResponseEntity<ApiResponse<?>> getOrders(PageableRequest request) {
        Sort sort = request.getSort().equals("asc") ? Sort.by("createdAt").ascending() : Sort.by("createdAt").descending() ;
        Pageable pageable = PageRequest.of(request.getPage(), request.getLimit(), sort);
        Page<Orders> orders = orderRepository.findSByStatus(OrderStatus.valueOf(request.getStatus()), pageable);
        List<OrderDTO> orderDTOList = orders.getContent().stream().map(
                item -> OrderDTO.builder()
                        .id(item.getId())
                        .name(item.getName())
                        .createdAt(item.getCreatedAt())
                        .status(item.getStatus())
                        .amount(item.getAmount())
                        .build()
        ).toList();
        return ResponseEntity.ok(new ApiResponse<>(200, "ok", orderDTOList));
    }
}
