package ptithcm.tttn.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@AllArgsConstructor

public class QuantityInventoryRsp {

    private String product_id;
    private String product_name;
    private Integer quantity;
    private BigDecimal total_import;
    private BigDecimal total_export;
    private BigDecimal current_stock;
}
