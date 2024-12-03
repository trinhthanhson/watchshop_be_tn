package ptithcm.tttn.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ptithcm.tttn.entity.Cart_detail;
import ptithcm.tttn.entity.Customer;
import ptithcm.tttn.entity.User;
import ptithcm.tttn.repository.CartDetailRepo;
import ptithcm.tttn.response.GetAllProductCouponRsp;
import ptithcm.tttn.service.CartDetailService;
import ptithcm.tttn.service.CustomerService;
import ptithcm.tttn.service.UserService;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartDetailServiceImpl implements CartDetailService {

    private final CartDetailRepo cartDetailRepo;
    private final UserService userService;
    private final CustomerService customerService;

    @Override
    @Transactional
    public void createCart(Cart_detail cartDetail, String jwt) throws Exception {
        User find = userService.findUserByJwt(jwt);
        Customer customer = customerService.findByUserId(find.getUser_id());
        List<Cart_detail> findCart = findCartByJwt(jwt);

        Cart_detail existingCart = findCart.stream()
                .filter(cart -> cart.getProduct_id().equals(cartDetail.getProduct_id()))
                .findFirst()
                .orElse(null);

        if (existingCart != null) {
            existingCart.setQuantity(existingCart.getQuantity() + cartDetail.getQuantity());
            cartDetailRepo.save(existingCart);
        } else {
            Cart_detail create = new Cart_detail();
            create.setCustomer_id(customer.getCustomer_id());
            create.setQuantity(cartDetail.getQuantity());
            create.setProduct_id(cartDetail.getProduct_id());
            cartDetailRepo.save(create);
        }
    }


    @Override
    public List<Cart_detail> findCartByJwt(String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Customer customer = customerService.findByUserId(user.getUser_id());
        return cartDetailRepo.findAllByCustomerId(customer.getCustomer_id());
    }

    @Override
    public void updateQuantityCart(Cart_detail cartDetail, String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Customer customer = customerService.findByUserId(user.getUser_id());
        Cart_detail cart = cartDetailRepo.findPosCart(customer.getCustomer_id(), cartDetail.getProduct_id());
        cart.setQuantity(cart.getQuantity() + cartDetail.getQuantity());
        cartDetailRepo.save(cart);
    }

    public Cart_detail findPosCart(Cart_detail cartDetail, String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Customer customer = customerService.findByUserId(user.getUser_id());
        return cartDetailRepo.findPosCart(customer.getCustomer_id(), cartDetail.getProduct_id());
    }

    @Override
    public void deleteCartItem(Cart_detail cartDetail, String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Customer customer = customerService.findByUserId(user.getUser_id());
        Cart_detail find = cartDetailRepo.findPosCart(customer.getCustomer_id(), cartDetail.getProduct_id());
        cartDetailRepo.delete(find);
    }

    @Override
    public void deleteCart(String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Customer customer = customerService.findByUserId(user.getUser_id());
        cartDetailRepo.deleteCartByCustomerId(customer.getCustomer_id());
    }

    @Override
    public List<GetAllProductCouponRsp> getProductCouponInCartDetail(String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Customer customer = customerService.findByUserId(user.getUser_id());
        List<Object[]> results = cartDetailRepo.getProductCouponInCartDetail(customer.getCustomer_id());
        return results.stream()
                .map(this::mapToGetAllProductCoupon)
                .collect(Collectors.toList());
    }

    private GetAllProductCouponRsp mapToGetAllProductCoupon(Object[] result) {

        String product_id = (String) result[0];
        String product_name = (String) result[1];
        String image = (String) result[2];
        Integer quantity = (Integer) result[3];
        Integer quantityCart = (Integer) result[4];
        Double discounted_price = (Double) result[5];
        BigInteger current_price = (BigInteger) result[6];

        return new GetAllProductCouponRsp(product_id, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, product_name, quantityCart,quantity,
                null, null, image, null, null, null, null, null, null, null,
                null, null,current_price , discounted_price);
    }

}
