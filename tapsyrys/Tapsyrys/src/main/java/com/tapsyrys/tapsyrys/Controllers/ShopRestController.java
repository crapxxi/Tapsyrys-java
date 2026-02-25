package com.tapsyrys.tapsyrys.Controllers;

import com.tapsyrys.tapsyrys.DTO.ShopResponse;
import com.tapsyrys.tapsyrys.Models.Shop;
import com.tapsyrys.tapsyrys.Repositories.ShopRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/shops")
@Tag(name="Управление магазинами", description = "Получение и авторизация магазинов")
public class ShopRestController {
    private final ShopRepository shopRepository;

    ShopRestController(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @GetMapping("/info")
    @Transactional(readOnly = true)
    @Operation(summary = "Получение информации о магазине", description = "Получение информации с номера")
    public ShopResponse getInfoByPhone(@RequestParam String phone) {
        String cleanPhone = phone.trim();

        if (cleanPhone.startsWith("8")) {
            cleanPhone = "+7" + cleanPhone.substring(1);
        } else if (cleanPhone.startsWith("7") && cleanPhone.length() == 11) {
            cleanPhone = "+" + cleanPhone;
        }
        return shopRepository.findByPhone(cleanPhone).map(
            shop -> new ShopResponse(shop.getId(), shop.getName(),shop.getUsername(), shop.getLocation(), shop.getPhone())
        ).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Таких магазинов нет"));
    }

    @PostMapping("/register")
    @Transactional
    @Operation(summary="Регистрация магазина")
    public ResponseEntity<?> registerShop(@RequestBody ShopResponse shopResponse) {
        Shop shop = new Shop();

        shop.setName(shopResponse.name());
        shop.setLocation(shopResponse.location());
        shop.setUsername(shopResponse.username());
        String phone = shopResponse.phone().trim();
        if (phone.startsWith("8")) {
            phone = "+7" + phone.substring(1);
        }
        shop.setPhone(phone);

        Shop savedShop = shopRepository.save(shop);
        ShopResponse response = new ShopResponse(
                savedShop.getId(),
                savedShop.getName(),
                savedShop.getUsername(),
                savedShop.getLocation(),
                savedShop.getPhone()
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
