package ptithcm.tttn.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;

@Setter
@Getter
@AllArgsConstructor
public class RequestNotFullRsp {
    private BigInteger requestId;
    private String productId;
    private Integer  price;
    private String note;
    private Integer  productQuantity;
    private String productName;
    private Integer  quantityRequest;
    private BigDecimal totalTransactedQuantity;
    private BigDecimal remainingQuantity;
}
