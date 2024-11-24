package ptithcm.tttn.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ProductTransRequest {
    private String productId;
    private int quantity_request;
    private int quantity;
    private int unitPrice;
    private String note;
    private int stock;
}
