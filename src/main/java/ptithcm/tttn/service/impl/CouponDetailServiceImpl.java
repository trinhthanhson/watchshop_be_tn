package ptithcm.tttn.service.impl;

import org.springframework.stereotype.Service;
import ptithcm.tttn.entity.Coupon_detail;
import ptithcm.tttn.function.Status;
import ptithcm.tttn.repository.CouponDetailRepo;
import ptithcm.tttn.service.CouponDetailService;

import java.util.Optional;

@Service
public class CouponDetailServiceImpl implements CouponDetailService {
    private  final CouponDetailRepo couponDetailRepo;

    public CouponDetailServiceImpl(CouponDetailRepo couponDetailRepo) {
        this.couponDetailRepo = couponDetailRepo;
    }

    @Override
    public Coupon_detail findById(Long id) throws Exception {
        Optional<Coupon_detail> find = couponDetailRepo.findById(id);
        if(find.isPresent()){
            return find.get();
        }
        throw new Exception("Not found coupon detail by id " + id);
    }
}
