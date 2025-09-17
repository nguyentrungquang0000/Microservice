package com.example.product_service.product;

import com.example.product_service.S3.FileResponse;
import com.example.product_service.S3.S3Client;
import com.example.product_service.entity.Product;
import com.example.product_service.entity.ProductFile;
import com.example.product_service.entity.ProductOption;
import com.example.product_service.product_file.ProductFileRepository;
import com.example.product_service.product_option.ProductOptionMapper;
import com.example.product_service.product_option.ProductOptionRepository;
import com.example.product_service.product_option.ProductOptionRequest;
import com.example.search.ApiResponse;
import com.example.search.SearchRequest;
import com.example.search.SearchResponse;
import com.example.search.detail.OptionDetail;
import com.example.search.detail.ProductDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductMapper productMapper;
    private final ProductOptionMapper productOptionMapper;

    private final ProductRepository productRepository;
    private final ProductOptionRepository productOptionRepository;
    private final ProductFileRepository productFileRepository;

    private final S3Client s3Client;
    @Transactional
    public ResponseEntity<String> createProduct(ProductRequest request, List<MultipartFile> files) {
        Product product = productRepository.save(productMapper.toProduct(request));
        for (ProductOptionRequest option : request.getOptions()){
            ProductOption productOption = productOptionMapper.toProductOption(option);
            productOption.setProduct(product);
            productOptionRepository.save(productOption);
        }
        for (MultipartFile file : files){
            String key = "product" + "*" + product.getId();
            FileResponse fileResponse = s3Client.uploadFile(file, key);
            ProductFile productFile = new ProductFile();
            productFile.setFileUrl(fileResponse.getFileUrl());
            productFile.setKey(fileResponse.getKey());
            productFile.setType(fileResponse.getType());
            productFile.setProduct(product);
            productFileRepository.save(productFile);
        }
        return ResponseEntity.ok("Product created");
    }
    @Transactional
    public ResponseEntity<String> deleteProduct(String productId) {
        Product product = productRepository.findById(productId).orElseThrow();
        String key = "product" + "*" + productId;
        s3Client.deleteAll(key);
        productRepository.delete(product);
        return ResponseEntity.ok("Product deleted");
    }

    public ResponseEntity<ApiResponse<?>> getProducts(SearchRequest request) {
        Sort sort = request.direction.equals("asc") ? Sort.by(request.sortBy).ascending() : Sort.by(request.sortBy).descending();
        Pageable pageable = PageRequest.of(request.page, request.limit, sort);
        Page<Product> page = productRepository.findProductByNameContainingIgnoreCase(request.keyword, pageable);
        List<SearchResponse> content= page.getContent().stream()
                .map(product -> new SearchResponse(
                        product.getId(),
                        product.getProductFiles().getFirst().getFileUrl(),
                        product.getName(),
                        product.getProductOptions().getFirst().getPrice(),
                        product.getProductOptions().getLast().getPrice(),
                        product.getCategory().getName(),
                        product.getAverageRating(),
                        product.getSoldQuantity()
                ))
                .collect(Collectors.toList());
        //Response
        Map<String, Object> response = new HashMap<>();
        response.put("content", content);
        response.put("pageNumber", page.getPageable().getPageNumber());
        response.put("totalPages", page.getTotalPages());
        response.put("totalElements", page.getTotalElements());
        response.put("currentPage", page.getNumber());
        response.put("size", page.getSize());
        return ResponseEntity.ok(new ApiResponse<>(200, "ok", response));
    }

    public ResponseEntity<ApiResponse<?>> getProductById(String productId) {
        Product product = productRepository.findById(productId).orElseThrow();
        List<OptionDetail> options = new ArrayList<>();
        for(ProductOption option : product.getProductOptions()){
            OptionDetail optionDetail = OptionDetail.builder()
                    .id(option.getId())
                    .name(option.getName())
                    .discount(option.getDiscount())
                    .price(option.getPrice())
                    .stock(option.getStock())
                    .build();
            options.add(optionDetail);
        }
        ProductDetail productDetail = ProductDetail.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .imageUrl(product.getProductFiles().stream().map(ProductFile::getFileUrl).collect(Collectors.toList()))
                .reviewCount(product.getReviewCount())
                .priceMin(product.getProductOptions().getFirst().getPrice())
                .priceMax(product.getProductOptions().getLast().getPrice())
                .averageRating(product.getAverageRating())
                .soldQuantity(product.getSoldQuantity())
                .options(options)
                .build();
        return ResponseEntity.ok(new ApiResponse<>(200, "ok", productDetail));
    }
}
