package com.tapsyrys.tapsyrys.Controllers;

import com.tapsyrys.tapsyrys.DTO.Responses.ProductResponse;
import com.tapsyrys.tapsyrys.Models.Product;
import com.tapsyrys.tapsyrys.Repositories.ProductRepository;
import com.tapsyrys.tapsyrys.Repositories.SupplierProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/v1/products")
@Tag(name="Продукты", description = "Управление продуктами.")
public class ProductRestController {
    private final ProductRepository productRepository;

    public ProductRestController(ProductRepository productRepository, SupplierProductRepository supplierProductRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/category")
    @Transactional(readOnly = true)
    @Operation(summary = "Получение товаров по категориям")
    public List<ProductResponse> getProductsByCategory(@RequestParam String category) {
        return productRepository.findAllByCategoryContainingIgnoreCase(category).stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getFirm(),
                        product.getCategory()
                )).toList();
    }

    @GetMapping("/name")
    @Transactional(readOnly = true)
    @Operation(summary = "Получение товара по имени")
    public List<ProductResponse> getProductByName(@RequestParam String name) {
        return productRepository.findAllByNameContainingIgnoreCase(name).stream()
                .map( product ->  new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getFirm(),
                        product.getCategory()
                )).toList();
    }

    @PostMapping("/add")
    @Transactional
    @Operation(summary = "Загрузка товаров")
    public ResponseEntity<?> addProduct(@RequestBody ProductResponse productResponse) {
        Product product = new Product();
        product.setName(productResponse.name());
        product.setFirm(productResponse.firm());
        product.setCategory(productResponse.category());

        Product savedProduct = productRepository.save(product);
        ProductResponse response = new ProductResponse(savedProduct.getId(), savedProduct.getName(), savedProduct.getFirm(), savedProduct.getCategory());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @Transactional(readOnly = true)
    @Operation(summary = "Получить все товары")
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(
                product -> new ProductResponse(product.getId(), product.getName(), product.getFirm(), product.getCategory())
        ).toList();
    }

}
