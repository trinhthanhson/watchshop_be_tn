package ptithcm.tttn.service;

import ptithcm.tttn.entity.Cart_detail;

import java.util.List;

public interface CartDetailService {

    void createCart(Cart_detail cartDetail, String jwt) throws Exception;

    List<Cart_detail> findCartByJwt(String jwt) throws Exception;

    void updateQuantityCart(Cart_detail cartDetail,String jwt) throws Exception;

    Cart_detail findPosCart(Cart_detail cartDetail,String jwt) throws Exception;
}
