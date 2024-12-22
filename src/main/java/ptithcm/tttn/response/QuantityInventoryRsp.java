package ptithcm.tttn.response;

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

public class QuantityInventoryRsp {

    private String product_id;
    private String product_name;
    private String image;
    private Integer quantity;
    private Long total_import;
    private Long total_export;
    private Long current_stock;
   private String info;
 }
