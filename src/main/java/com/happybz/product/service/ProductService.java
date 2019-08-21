package com.happybz.product.service;

import com.happybz.product.dataobject.ProductInfo;
import com.happybz.product.dto.CartDTO;

import java.util.List;

public interface ProductService {

    /**
     * 查询所有在架商品列表
     */
    List<ProductInfo> findUpAll();

    /**
     * 查询商品列表
     * @param
     * @return
     */
    List<ProductInfo> findList(List<String> productIdList);

    /**
     * 扣库存
     * @param
     */
    void decreaseStock(List<CartDTO> cartDTOList);
}
