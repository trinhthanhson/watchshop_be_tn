package ptithcm.tttn.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class GetDataAiTransaction {
    private String productId;
    private Integer week;
    private Date created_at;
    private BigDecimal importPrice;
    private Integer beginInventory;
    private BigDecimal importQuantity;
    private BigDecimal exportQuantity;
    private BigDecimal endQuantity;
    private BigDecimal exportPrice;
    private BigDecimal priceRatio;
}
