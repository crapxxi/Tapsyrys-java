package com.tapsyrys.tapsyrys.Repositories;

import com.tapsyrys.tapsyrys.RelationModels.Order;
import com.tapsyrys.tapsyrys.Models.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findAllByShop(Shop shop);
}
