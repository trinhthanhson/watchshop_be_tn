package ptithcm.tttn.service;

import ptithcm.tttn.entity.Cart_detail;
import ptithcm.tttn.response.GetAllProductCouponRsp;

import java.util.List;

public interface CartDetailService {

    void createCart(Cart_detail cartDetail, String jwt) throws Exception;

    List<Cart_detail> findCartByJwt(String jwt) throws Exception;

    void updateQuantityCart(Cart_detail cartDetail,String jwt) throws Exception;

    void deleteCartItem(Cart_detail cartDetail, String jwt) throws Exception;

    void deleteCart(String jwt) throws Exception;

    List<GetAllProductCouponRsp> getProductCouponInCartDetail(String jwt) throws Exception;
}
