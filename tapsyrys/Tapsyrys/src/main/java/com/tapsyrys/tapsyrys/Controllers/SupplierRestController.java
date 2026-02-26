package com.tapsyrys.tapsyrys.Controllers;

import com.tapsyrys.tapsyrys.DTO.SupplierResponse;
import com.tapsyrys.tapsyrys.Models.Supplier;
import com.tapsyrys.tapsyrys.Repositories.SupplierRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/suppliers")
@Tag(name="Управление поставщиками")
public class SupplierRestController {
    private final SupplierRepository supplierRepository;
    SupplierRestController(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @GetMapping("/get")
    @Operation(summary = "Ищем поставщика с telegram_id")
    public SupplierResponse getSupplierByPhone(@RequestParam Long telegramId) {
        return supplierRepository.findSupplierByTelegramId(telegramId)
                .map( supplier -> new SupplierResponse(supplier.getId(), supplier.getName(), supplier.getUsername(), supplier.getPhone(), supplier.getCategory(), supplier.getTelegramId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Такого поставщика нету"));
    }

    @PostMapping("/register")
    @Operation(summary = "Регистрируем поставщика")
    public ResponseEntity<?> registerSupplier(@RequestBody SupplierResponse supplierResponse) {
        Supplier supplier = new Supplier();
        supplier.setCategory(supplierResponse.category());
        supplier.setName(supplierResponse.name());
        supplier.setUsername(supplierResponse.username());
        supplier.setTelegramId(supplierResponse.telegramId());

        String phone = supplierResponse.phone().trim();
        if(phone.startsWith("8"))  phone = "+7" + phone.substring(1);
        supplier.setPhone(phone);

        Supplier savedSupplier = supplierRepository.save(supplier);

        SupplierResponse response = new SupplierResponse(savedSupplier.getId(), savedSupplier.getName(),savedSupplier.getUsername(),savedSupplier.getPhone(), savedSupplier.getCategory(), supplier.getTelegramId());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
