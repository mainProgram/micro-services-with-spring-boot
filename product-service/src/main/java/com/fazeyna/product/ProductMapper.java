package com.fazeyna.product;

import com.fazeyna.dtos.product.ProductRequest;
import com.fazeyna.dtos.product.ProductResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductEntity mapRequestToEntity(ProductRequest request){
        ProductEntity product = ProductEntity.builder()
                .libelle(request.getLibelle())
                .idCreateur(request.getIdCreateur())
                .build();
        return product;
    }

    public ProductResponse mapEntityToResponse(ProductEntity product){
        ProductResponse response = ProductResponse.builder()
                .id(product.getId())
                .libelle(product.getLibelle())
                .idCreateur(product.getIdCreateur())
                .build();
        return response;
    }

}
