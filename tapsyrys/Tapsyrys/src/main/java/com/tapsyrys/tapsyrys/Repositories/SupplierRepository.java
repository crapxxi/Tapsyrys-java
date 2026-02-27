package com.tapsyrys.tapsyrys.Repositories;

import com.tapsyrys.tapsyrys.Models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    List<Supplier> findAllByCategory(String category);
    Optional<Supplier> findSupplierByTelegramId(Long telegramId);
    Optional<Supplier> findSupplierByPhone(String phone);
}
