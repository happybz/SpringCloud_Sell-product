package com.happybz.product.service.impl;

//import com.happybz.product.common.DecreaseStockInput;
//import com.happybz.product.common.ProductInfoOutput;
import com.happybz.product.dataobject.ProductInfo;
import com.happybz.product.dto.CartDTO;
import com.happybz.product.enums.ProductStatusEnum;
import com.happybz.product.enums.ResultEnum;
//import com.happybz.product.exception.ProductException;
import com.happybz.product.exception.ProductException;
import com.happybz.product.repository.ProductInfoRepository;
import com.happybz.product.service.ProductService;
import com.happybz.product.utils.JsonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public List<ProductInfo> findList(List<String> productIdList) {
        return productInfoRepository.findByProductIdIn(productIdList);
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList){
            Optional<ProductInfo> productInfoOptional = productInfoRepository.findById(cartDTO.getProductId());
            //判断商品是否存在
            if (!productInfoOptional.isPresent()){
                throw new ProductException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            ProductInfo productInfo = productInfoOptional.get();
            //库存是否足够
            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (result < 0){
                throw new ProductException(ResultEnum.PRODUCT_STOCK_ERROR);
            }

            productInfo.setProductStock(result);
            productInfoRepository.save(productInfo);
        }
    }
}
