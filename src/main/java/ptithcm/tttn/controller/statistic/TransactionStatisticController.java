package ptithcm.tttn.controller.statistic;

import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.Type;
import ptithcm.tttn.response.DataAIRsp;
import ptithcm.tttn.response.ListEntityResponse;
import ptithcm.tttn.response.StatisticRsp;
import ptithcm.tttn.response.TransactionStatisticRsp;
import ptithcm.tttn.service.TransactionService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transaction/statistic")
@AllArgsConstructor
public class TransactionStatisticController {

    private final TransactionService transactionService;

    @GetMapping("/type")
    public ResponseEntity<ListEntityResponse<TransactionStatisticRsp>> getStatisticByTypeHandle(@RequestHeader("Authorization") String jwt, @RequestBody Type type) {
        ListEntityResponse<TransactionStatisticRsp> res = new ListEntityResponse<>();
        try {
            List<TransactionStatisticRsp> statistic = transactionService.getStatisticTransaction(type.getType_name());
            res.setData(statistic);
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
            res.setMessage("success");
        } catch (Exception e) {
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @GetMapping("/all")
    public ResponseEntity<ListEntityResponse<StatisticRsp>> getStatisticHandle(
            @RequestHeader("Authorization") String jwt,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        ListEntityResponse<StatisticRsp> res = new ListEntityResponse<>();
        try {
            // Nếu startDate hoặc endDate null, set giá trị mặc định (vd: lấy tất cả)
            if (startDate == null) {
                startDate = LocalDate.of(1970, 1, 1); // Hoặc giá trị mặc định khác
            }
            if (endDate == null) {
                endDate = LocalDate.now(); // Hoặc giá trị mặc định khác
            }

            // Gửi tham số startDate và endDate vào service
            List<StatisticRsp> statistic = transactionService.getAllStatistic(startDate, endDate);

            res.setData(statistic);
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
            res.setMessage("success");
        } catch (Exception e) {
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @GetMapping("/all/type")
    public ResponseEntity<ListEntityResponse<StatisticRsp>> getStatisticByTypeHandle(
            @RequestHeader("Authorization") String jwt,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "type_name") String typeName) { // Sử dụng @RequestParam cho type_name
        ListEntityResponse<StatisticRsp> res = new ListEntityResponse<>();
        try {
            // Gán giá trị mặc định nếu `startDate` hoặc `endDate` bị null
            if (startDate == null) {
                startDate = LocalDate.of(1970, 1, 1); // Ngày mặc định
            }
            if (endDate == null) {
                endDate = LocalDate.now(); // Ngày hiện tại
            }

            // Gửi tham số vào service
            List<StatisticRsp> statistic = transactionService.getAllStatisticByType(startDate, endDate, typeName);

            res.setData(statistic);
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
            res.setMessage("success");
        } catch (Exception e) {
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res, res.getStatus());
    }


    @GetMapping("/get/data")
    public ResponseEntity<ByteArrayResource> exportDataToExcel(@RequestHeader("Authorization") String jwt) {
        try {
            List<DataAIRsp> statistic = transactionService.getDataAI();

            // Tạo workbook
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Data AI");

            // Tạo header
            Row headerRow = sheet.createRow(0);
            String[] columns = {"productId", "week", "quantity", "difference_quantity", "price_volatility", "price"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            // Điền dữ liệu
            int rowNum = 1;
            for (DataAIRsp data : statistic) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(data.getProductId());
                row.createCell(1).setCellValue(data.getWeek());
                row.createCell(2).setCellValue(data.getQuantity().toString());
                if (data.getDifferenceQuantity() == null) {
                    data.setDifferenceQuantity(BigDecimal.valueOf(0));
                }
                row.createCell(3).setCellValue(data.getDifferenceQuantity().toString());
                row.createCell(4).setCellValue(data.getPriceVolatility());
                row.createCell(5).setCellValue(data.getPrice().toString());
            }

            // Lưu file Excel vào đường dẫn cố định
            String filePath = "C:\\Users\\ADMIN\\Desktop\\DATN\\DATN\\watchshop_be_tn\\data_ai.xlsx";
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }

            // Đóng workbook
            workbook.close();

            // Đọc file vừa lưu để trả về
            File file = new File(filePath);
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(file.toPath()));

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=data_ai.xlsx")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);

        } catch (Exception e) {
            // Xử lý lỗi trả về file rỗng kèm thông báo
            ByteArrayResource emptyResource = new ByteArrayResource(new byte[0]);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=error.txt")
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(new ByteArrayResource(("Error: " + e.getMessage()).getBytes(StandardCharsets.UTF_8)));
        }
    }







}
