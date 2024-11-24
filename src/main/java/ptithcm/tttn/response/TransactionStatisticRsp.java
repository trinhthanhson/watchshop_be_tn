package ptithcm.tttn.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
public class TransactionStatisticRsp {
    private Date createDate;
    private String transactionCode;
    private String watchId;
    private String watchName;
    private Integer price;
    private Integer startQty;
    private Integer actualQuantity;
    private BigInteger type_id;

}
