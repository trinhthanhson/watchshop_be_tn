package ptithcm.tttn.service;

import org.springframework.data.repository.query.Param;
import ptithcm.tttn.entity.Update_price;

import java.util.List;

public interface UpdatePriceService {
    Update_price updatePriceProduct( Update_price priceUpdateDetail, String jwt) throws Exception;

    List<Update_price> getPriceProduct();
    Update_price getPriceProductById( String product_id);


}
