package ptithcm.tttn.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartResponse {
    private String product_id;
    private String product_name;
    private int quantity;
    private int quantityProduct;
    private String image;
    private int current_price;
    private int discounted_price;
}
