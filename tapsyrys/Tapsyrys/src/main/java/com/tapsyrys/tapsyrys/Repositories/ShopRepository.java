package com.tapsyrys.tapsyrys.Repositories;

import com.tapsyrys.tapsyrys.Models.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    Optional<Shop> findByUsername(String username);
    Optional<Shop> findByPhone(String phone);
    Optional<Shop> findByTelegramId(Long telegram_id);
}
