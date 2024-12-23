package ptithcm.tttn.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ptithcm.tttn.entity.Staff;
import ptithcm.tttn.entity.Update_price;
import ptithcm.tttn.entity.User;
import ptithcm.tttn.repository.UpdatePriceRepo;
import ptithcm.tttn.service.ProductService;
import ptithcm.tttn.service.StaffService;
import ptithcm.tttn.service.UpdatePriceService;
import ptithcm.tttn.service.UserService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdatePriceServiceImpl implements UpdatePriceService {

    private final UpdatePriceRepo updatePriceRepo;
    private final UserService userService;
    private final StaffService staffService;

    @Override
    @Transactional
    public Update_price updatePriceProduct(Update_price priceUpdateDetail, String jwt) throws Exception {
        Update_price update = new Update_price();
        User user = userService.findUserByJwt(jwt);
        Staff staff = staffService.findByUserId(user.getUser_id());

        update.setProduct_id(priceUpdateDetail.getProduct_id());
        update.setCreated_at(LocalDateTime.now());
        update.setUpdated_by(staff.getStaff_id());
        update.setUpdated_at(priceUpdateDetail.getUpdated_at());
        update.setCreated_by(staff.getStaff_id());
        update.setPrice_new(priceUpdateDetail.getPrice_new());

        return updatePriceRepo.save(update);
    }

    @Override
    public List<Update_price> getPriceProduct() {
        return updatePriceRepo.getPriceProduct();
    }

    @Override
    public Update_price getPriceProductById(String product_id) {
        return updatePriceRepo.getPriceProductById(product_id);
    }
}
