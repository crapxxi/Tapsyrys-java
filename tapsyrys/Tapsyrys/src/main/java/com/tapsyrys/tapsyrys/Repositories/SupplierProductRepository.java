package com.tapsyrys.tapsyrys.Repositories;

import com.tapsyrys.tapsyrys.Models.Product;
import com.tapsyrys.tapsyrys.Models.Supplier;
import com.tapsyrys.tapsyrys.RelationModels.SupplierProduct;
import jakarta.persistence.Column;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierProductRepository extends JpaRepository<SupplierProduct, Long> {

    Optional<SupplierProduct> findFirstByProductNameContainingIgnoreCaseOrderByPriceAsc(String name);
    List<SupplierProduct> findAllBySupplierId(Long supplierId);
}
