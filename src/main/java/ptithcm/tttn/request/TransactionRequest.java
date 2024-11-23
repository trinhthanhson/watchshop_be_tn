package ptithcm.tttn.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class TransactionRequest {

    private String note;
    private String content;
    private int total_quantity;
    private int total_price;
    private String type_name;
    private List<ProductTransRequest> products;

}
