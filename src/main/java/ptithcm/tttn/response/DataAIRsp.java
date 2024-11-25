package ptithcm.tttn.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class DataAIRsp {
    private String productId;
    private String productName;
    private Integer week;
    private BigDecimal quantity;
    private BigDecimal differenceQuantity;
    private Double priceVolatility;
}
