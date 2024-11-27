package ptithcm.tttn.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
public class ProductSaleRequest {
    private String product_id;
    private String product_name;
    private BigDecimal total_sold;
    private BigDecimal total_quantity;
    private Date date_pay;
}
