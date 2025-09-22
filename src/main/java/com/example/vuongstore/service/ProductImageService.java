package com.example.vuongstore.service;

import com.example.vuongstore.dto.request.ProductImageRequest;
import com.example.vuongstore.dto.response.ProductResponse;
import com.example.vuongstore.entity.ProductImage;
import com.example.vuongstore.exception.AppException;
import com.example.vuongstore.exception.ErrorCode;
import com.example.vuongstore.repository.ProductImageRepository;
import com.example.vuongstore.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductImageService {
     ProductImageRepository productImageRepository;
     ProductService productService;
     @PreAuthorize("hasRole('ADMIN')")
     public void deleteProductImage(Long id){
         List<ProductImage> productImages = productImageRepository.findByProductId(id);
         if(!productImages.isEmpty()){
             productImageRepository.deleteAll(productImages);
         }
     }




     @PreAuthorize("hasRole('ADMIN')")
    public List<ProductImage> uploadProductImages(List<MultipartFile> files, Long id) throws IOException {
        ProductResponse productExisting = productService.getProductById(id);
        files = files == null ? new ArrayList<>() : files;
        if(files.size() > ProductImage.MAXIMUM_IMAGE_COUNT){
            throw new AppException(ErrorCode.MAXIMUM_IMAGE_COUNT_EXCEEDED);
        }
        if(files.isEmpty() || files.stream().allMatch(file -> file.getSize() == 0)){
            throw new AppException(ErrorCode.INVALID_IMAGE);
        }
        List<ProductImage> productImages = new ArrayList<>();
        for(MultipartFile file :files){
            if(file.getSize() == 0){
                continue;
            }
            if(file.getSize() > 1024 *1024 * 5) { //5MB
                throw new AppException(ErrorCode.INVALID_IMAGE_SIZE);
            }
            String contentType = file.getContentType();
            if(contentType == null || !contentType.startsWith("image/")){
                throw new AppException(ErrorCode.INVALID_IMAGE_URL);
            }
            String fileName = storeFile(file);
            ProductImage productImage = productService.createProductImage(
                    productExisting.getId(),
                    ProductImageRequest.builder()
                            .url(fileName)
                            .build()
            );
            productImages.add(productImage);
        }
        return productImages;
    }

    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    public String storeFile(MultipartFile file) throws IOException {
        if(!isImageFile(file) && file.getOriginalFilename() == null){
            throw new AppException(ErrorCode.INVALID_IMAGE_URL);
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
        Path uploadDir = Paths.get("uploads");
        if(!Files.exists(uploadDir)){
            Files.createDirectories(uploadDir);
        }
        Path destinationFile = Paths.get(uploadDir.toString(), uniqueFileName);
        Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }
}
