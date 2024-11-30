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
    private BigDecimal total_import;
    private BigDecimal total_export;
    private BigDecimal current_stock;
    private Date period_value; // Giá trị tuần, tháng hoặc năm
    private Date date_range;    // Chuỗi thể hiện khoảng thời gian (từ ngày đến ngày)
 }
