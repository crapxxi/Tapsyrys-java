package com.tapsyrys.tapsyrys.Repositories;

import com.tapsyrys.tapsyrys.Models.RelationModels.SupplierProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierProductRepository extends JpaRepository<SupplierProduct, Long> {

    Optional<SupplierProduct> findFirstBySupplierIdAndProductNameContainingIgnoreCaseOrderByPriceAsc(Long supplierId, String name);
    List<SupplierProduct> findAllBySupplierId(Long supplierId);
}
