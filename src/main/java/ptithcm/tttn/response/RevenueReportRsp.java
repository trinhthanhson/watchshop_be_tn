package ptithcm.tttn.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@AllArgsConstructor
public class RevenueReportRsp {
    private LocalDateTime transactionDate;
    private BigDecimal totalRevenue;
    private BigDecimal totalQuantitySold;
}
