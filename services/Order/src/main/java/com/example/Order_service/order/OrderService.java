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
import com.example.Order_service.order.modal.request.AddOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;


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
    public ResponseEntity<Object> addOrder(AddOrderRequest request, String userId) {
        OrderConfirmation orderConfirmation = new OrderConfirmation();
        List<ProductOptionResponse> productOptionResponseList = new ArrayList<>();
        Orders order = new Orders();
        order.setCustomerId(userId);
        order.setMethod("PAY");
        AddressDTO addressDTO = addressClient.getAddress(request.getAddressId());
        order.setName(addressDTO.getName());
        order.setCity(addressDTO.getCity());
        order.setWard(addressDTO.getWard());
        order.setAddress(addressDTO.getAddress());
        order.setPhone(addressDTO.getPhone());
        order.setDescription(request.getDescription());

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
        orderConfirmation.setOrderId(order.getId());
        orderConfirmation.setAmount(amout);
        orderConfirmation.setAddress(addressDTO);
        orderConfirmation.setPaymentMethod("");
        orderConfirmation.setProductOptions(productOptionResponseList);

        orderProducer.sendOrderConfirmation(orderConfirmation);

        orderRepository.save(order);

        return ResponseEntity.ok(new ApiResponse<>(200, "ok", amout));
    }
}
