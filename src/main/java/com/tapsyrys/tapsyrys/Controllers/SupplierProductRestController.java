package com.tapsyrys.tapsyrys.Controllers;

import com.tapsyrys.tapsyrys.DTO.Responses.SupplierProductResponse;
import com.tapsyrys.tapsyrys.Models.RelationModels.SupplierProduct;
import com.tapsyrys.tapsyrys.Repositories.ProductRepository;
import com.tapsyrys.tapsyrys.Repositories.SupplierProductRepository;
import com.tapsyrys.tapsyrys.Repositories.SupplierRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@Tag(name = "Управление товарами поставщика")
public class SupplierProductRestController {
    private final SupplierProductRepository supplierProductRepository;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;

    SupplierProductRestController(SupplierProductRepository supplierProductRepository, SupplierRepository supplierRepository, ProductRepository productRepository) {
        this.supplierProductRepository = supplierProductRepository;
        this.supplierRepository = supplierRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/all")
    @Transactional(readOnly = true)
    @Operation(summary = "Получение каталога от поставщика", description = "Возвращает все товары с ценами и количеством")
    public List<SupplierProductResponse> getProductsBySupplier(@RequestParam Long supplierId) {
        return supplierProductRepository.findAllBySupplierId(supplierId).stream().map(
                supplierProduct -> new SupplierProductResponse(
                        supplierProduct.getProduct().getId(),
                        supplierProduct.getProduct().getId(),
                        supplierProduct.getSupplier().getId(),
                        supplierProduct.getProduct().getName(),
                        supplierProduct.getProduct().getFirm(),
                        supplierProduct.getCount(),
                        supplierProduct.getPrice()
                )).toList();
    }

    @GetMapping("/details")
    @Transactional(readOnly = true)
    @Operation(summary = "Получение данных о товаре от поставщика")
    public SupplierProductResponse getProductInfoBySupplierIdAndName(@RequestParam Long supplierId, @RequestParam String productName) {
        return supplierProductRepository.findFirstBySupplierIdAndProductNameContainingIgnoreCaseOrderByPriceAsc(supplierId, productName).map(
                supplierProduct -> new SupplierProductResponse(supplierProduct.getId(),supplierProduct.getProduct().getId(), supplierProduct.getSupplier().getId(), supplierProduct.getProduct().getName(), supplierProduct.getProduct().getFirm(), supplierProduct.getCount(), supplierProduct.getPrice())
        ).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Нету такого товара у поставщика"));
    }

    @PostMapping("/add")
    @Transactional
    @Operation(summary = "Добавление товара поставщика")
    public ResponseEntity<?> addSupplierProduct(@RequestBody SupplierProductResponse supplierProductResponse) {
        SupplierProduct supplierProduct = new SupplierProduct();
        supplierProduct.setSupplier(supplierRepository.getReferenceById(supplierProductResponse.supplierId()));
        supplierProduct.setProduct(productRepository.getReferenceById(supplierProductResponse.productId()));
        supplierProduct.setPrice(supplierProductResponse.price());
        supplierProduct.setCount(supplierProductResponse.count());

        SupplierProduct savedSupplierProductResponse = supplierProductRepository.save(supplierProduct);
        SupplierProductResponse response = new SupplierProductResponse(
                savedSupplierProductResponse.getId(),
                savedSupplierProductResponse.getProduct().getId(),
                savedSupplierProductResponse.getSupplier().getId(),
                savedSupplierProductResponse.getProduct().getName(),
                savedSupplierProductResponse.getProduct().getFirm(),
                savedSupplierProductResponse.getCount(),
                savedSupplierProductResponse.getPrice()
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
