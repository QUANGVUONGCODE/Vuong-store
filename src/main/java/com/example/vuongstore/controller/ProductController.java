package com.example.vuongstore.controller;

import com.example.vuongstore.dto.request.ProductRequest;
import com.example.vuongstore.dto.request.requestUpdate.ProductRequestUpdate;
import com.example.vuongstore.dto.response.ApiResponse;
import com.example.vuongstore.dto.response.ProductListResponse;
import com.example.vuongstore.dto.response.ProductResponse;
import com.example.vuongstore.entity.ProductImage;
import com.example.vuongstore.exception.AppException;
import com.example.vuongstore.exception.ErrorCode;
import com.example.vuongstore.service.ProductImageService;
import com.example.vuongstore.service.ProductService;
import com.example.vuongstore.entity.Product;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("${api.prefix}/products")
public class ProductController {
    ProductService productService;
    ProductImageService productImageService;
    String IMAGE_DIRECTORY = "uploads";

    @PostMapping
     ApiResponse<ProductResponse> createProduct(@RequestBody ProductRequest request) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.createProduct(request))
                .build();
    }

    @GetMapping("/{id}")
     ApiResponse<ProductResponse> getProductById(@PathVariable Long id) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.getProductById(id))
                .build();
    }

    @GetMapping
    ApiResponse<ProductListResponse> getAllProducts(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0", name = "category_id") Long categoryId,
            @RequestParam(defaultValue = "0", name = "brand_id") Long brandId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ){
        PageRequest pageRequest = PageRequest.of(
                page,
                limit,
                Sort.by("id").ascending()
        );
        Page<ProductResponse> productPage = productService.getAllProducts(keyword, categoryId, brandId, pageRequest);
        List<ProductResponse> products = productPage.getContent();

        int totalPages = productPage.getNumber() + 1;
        return ApiResponse.<ProductListResponse>builder()
                .result(ProductListResponse.builder()
                        .products(products)
                        .page(totalPages)
                        .build())
                .build();
    }

    @PutMapping("/{id}")
     ApiResponse<ProductResponse> updateProduct(@RequestBody ProductRequestUpdate request, @PathVariable Long id) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.updateProduct(request, id))
                .build();
    }

    @DeleteMapping("/{id}")
     ApiResponse<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ApiResponse.<String>builder()
                .result("Product deleted successfully")
                .build();
    }

    @GetMapping("/by-ids")
    ApiResponse<?> findProductsByIds(@RequestParam String ids) {
        try{
            List<Long> productIds = Arrays.stream(ids.split(",")).map(Long::parseLong).collect(Collectors.toList());
            return ApiResponse.<List<Product>>builder()
                    .result(productService.getProductsByIds(productIds))
                    .build();
        }catch (Exception e){
            return ApiResponse.<String>builder()
                    .result(e.getMessage())
                    .build();
        }
    }

    //Post image for product
    @PostMapping("upload/{id}")
    ApiResponse<?> uplaodProductImage(@PathVariable Long id, @RequestParam("files")List<MultipartFile> files) throws IOException {
        try{
            List<ProductImage> productImages = productImageService.uploadProductImages(files, id);
            return ApiResponse.<List<ProductImage>>builder()
                    .result(productImages)
                    .build();
        }catch (AppException e) {
            return ApiResponse.<String>builder()
                    .result(e.getMessage())
                    .build();
        }
    }

    @GetMapping("images/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(IMAGE_DIRECTORY).resolve(filename);
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                throw new AppException(ErrorCode.INVALID_IMAGE);
            }

            String contentType = Files.probeContentType(filePath);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } catch (Exception e) {
            throw new AppException(ErrorCode.INVALID_IMAGE);
        }
    }
}
