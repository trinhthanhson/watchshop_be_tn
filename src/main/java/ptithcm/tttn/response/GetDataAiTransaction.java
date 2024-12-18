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
    private Date startDate;
    private Date endDate;
    private BigDecimal importPrice;
    private BigDecimal importQuantity;
    private BigDecimal exportQuantity;
    private BigDecimal exportPrice;
    private BigDecimal priceVolatility;
    private BigDecimal quantityDifference;
    private BigDecimal endQuantity;
}
