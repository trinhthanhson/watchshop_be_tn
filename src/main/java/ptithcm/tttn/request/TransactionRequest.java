package ptithcm.tttn.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class TransactionRequest {

    private String content;
    private int total_quantity;
    private int total_price;
    private Long request_id;
    private String type_name;
    private String bill_code;
    private Long supplier_id;
    private List<ProductTransRequest> products;

}
