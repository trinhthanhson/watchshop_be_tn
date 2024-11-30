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
public class RevenueProductReportRsp {
    private String product_id;
    private String product_name;
    private String image;
    private BigDecimal total_sold;
    private BigDecimal total_quantity;
    private String period_value; // Giá trị tuần, tháng hoặc năm
    private Date date_range;    // Chuỗi thể hiện khoảng thời gian (từ ngày đến ngày)

}
