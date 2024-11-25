package ptithcm.tttn.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class StatisticRsp {
    private String productCode;      // Mã sản phẩm
    private String productName;      // Tên sản phẩm
    private BigDecimal openingQty;      // Số lượng tồn đầu kỳ
    private BigDecimal openingValue;     // Giá trị tồn đầu kỳ
    private BigDecimal importQty;       // Số lượng nhập trong kỳ
    private BigDecimal importValue;      // Giá trị nhập trong kỳ
    private BigDecimal exportQty;       // Số lượng xuất trong kỳ
    private BigDecimal exportValue;      // Giá trị xuất trong kỳ
    private BigDecimal closingQty;      // Số lượng tồn cuối kỳ
    private BigDecimal closingValue;     // Giá trị tồn cuối kỳ
}
