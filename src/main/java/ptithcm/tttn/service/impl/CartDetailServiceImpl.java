package ptithcm.tttn.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ptithcm.tttn.entity.Cart_detail;
import ptithcm.tttn.entity.Customer;
import ptithcm.tttn.entity.User;
import ptithcm.tttn.repository.CartDetailRepo;
import ptithcm.tttn.service.CartDetailService;
import ptithcm.tttn.service.CustomerService;
import ptithcm.tttn.service.UserService;

import javax.transaction.Transactional;
import java.util.List;

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
        Cart_detail find = cartDetailRepo.findPosCart(customer.getCustomer_id(),cartDetail.getProduct_id());
        cartDetailRepo.delete(find);
    }

    @Override
    public void deleteCart(String jwt) throws Exception {
        User user = userService.findUserByJwt(jwt);
        Customer customer = customerService.findByUserId(user.getUser_id());
        cartDetailRepo.deleteCartByCustomerId(customer.getCustomer_id());
    }
}
