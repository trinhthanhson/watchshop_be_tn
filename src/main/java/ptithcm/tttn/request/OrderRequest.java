package ptithcm.tttn.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ptithcm.tttn.response.CartResponse;
import ptithcm.tttn.response.GetAllProductCouponRsp;

import java.util.List;

@Data
@Getter
@Setter

public class OrderRequest {
    private List<CartResponse> cart;
    private String product_id;
    private int quantity;
    private int price;
    private int total_price;
    private String address;
    private String recipient_name;
    private String note;
    private String recipient_phone;
}
