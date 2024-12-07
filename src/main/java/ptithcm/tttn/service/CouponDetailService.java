package ptithcm.tttn.service;

import ptithcm.tttn.entity.Coupon_detail;

public interface CouponDetailService {

    Coupon_detail findById(Long id) throws Exception;
}
