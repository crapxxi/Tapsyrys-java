package com.tapsyrys.tapsyrys.DTO.Responses;

public record SupplierProductResponse(Long id, Long productId, Long supplierId,String name, String firm, Integer count, Integer price) { }
