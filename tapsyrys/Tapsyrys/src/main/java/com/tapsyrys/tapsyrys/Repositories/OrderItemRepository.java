package com.tapsyrys.tapsyrys.Repositories;

import com.tapsyrys.tapsyrys.RelationModels.Order;
import com.tapsyrys.tapsyrys.RelationModels.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Optional<OrderItem> findByOrder(Order order);
}
