package com.example.vuongstore.service;

import com.example.vuongstore.dto.request.ProductImageRequest;
import com.example.vuongstore.dto.request.ProductRequest;
import com.example.vuongstore.dto.request.requestUpdate.ProductRequestUpdate;
import com.example.vuongstore.dto.response.ProductResponse;
import com.example.vuongstore.entity.Brands;
import com.example.vuongstore.entity.Category;
import com.example.vuongstore.entity.Product;
import com.example.vuongstore.entity.ProductImage;
import com.example.vuongstore.exception.AppException;
import com.example.vuongstore.exception.ErrorCode;
import com.example.vuongstore.mapper.ProductMapper;
import com.example.vuongstore.repository.BrandsRepository;
import com.example.vuongstore.repository.CategoryRepository;
import com.example.vuongstore.repository.ProductImageRepository;
import com.example.vuongstore.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class ProductService {
    ProductRepository productRepository;
    ProductMapper productMapper;
    CategoryRepository categoryRepository;
    ProductImageRepository productImageRepository;
    BrandsRepository brandsRepository;
    public ProductResponse createProduct(ProductRequest productRequest) {
        Category existingCategory = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID));
        Brands existingBrand = brandsRepository.findById(productRequest.getBrandId()).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID)
        );
        if(productRepository.existsByName(productRequest.getName())){
            throw new AppException(ErrorCode.PRODUCT_EXISTS);
        }
        Product product = productMapper.mapToProduct(productRequest);
        product.setCategory(existingCategory);
        product.setBrands(existingBrand);
        product.prePersist();
        return productMapper.toProductResponse(productRepository.save(product));
    }

    public ProductResponse getProductById(Long id) {
        return productMapper.toProductResponse(productRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID)));
    }

    public Page<ProductResponse> getAllProducts(String keyword, Long categoryId,Long brandId, PageRequest pageRequest) {
        return productRepository.findAll(pageRequest, categoryId,brandId, keyword)
                .map(productMapper::toProductResponse);
    }

    public ProductResponse updateProduct(ProductRequestUpdate request, Long id){
        Product product = productRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID)
        );
        productMapper.updateProductFromRequest(request, product);
        product.preUpdate();
        return productMapper.toProductResponse(productRepository.save(product));
    }

    public void deleteProduct(Long id) {
        if(!productRepository.existsById(id)){
            throw new AppException(ErrorCode.INVALID_ID);
        }
        productRepository.deleteById(id);
    }

    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    public List<Product> getProductsByIds(List<Long> productIds) {
        return productRepository.findByIdIn(productIds);
    }

    public List<ProductImage> getProductImageById(Long id){
        return productImageRepository.findByProductId(id);
    }

    public ProductImage getProductImageByThumbnail(String thumbnail) {
        return productImageRepository.findByUrl(thumbnail).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_THUMBNAIL)
        );
    }

    public ProductImage createProductImage(Long productId, ProductImageRequest request) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID)
        );

        ProductImage productImage = ProductImage.builder()
                .product(product)
                .url(request.getUrl())
                .build();
        int size = productImageRepository.findByProductId(productId).size();
        if(size >= ProductImage.MAXIMUM_IMAGE_COUNT){
            throw new AppException(ErrorCode.MAXIMUM_IMAGE_COUNT_EXCEEDED);
        }
        return productImageRepository.save(productImage);
    }
}
