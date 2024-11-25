package ptithcm.tttn.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ptithcm.tttn.entity.*;
import ptithcm.tttn.repository.*;
import ptithcm.tttn.request.ProductTransRequest;
import ptithcm.tttn.request.TransactionRequest;
import ptithcm.tttn.response.StatisticRsp;
import ptithcm.tttn.response.TransactionStatisticRsp;
import ptithcm.tttn.service.ProductService;
import ptithcm.tttn.service.StaffService;
import ptithcm.tttn.service.TransactionService;
import ptithcm.tttn.service.UserService;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepo transactionRepo;
    private final TypeRepo typeRepo;
    private final UserService userService;
    private final StaffService staffService;
    private final BillSupplierRepo billSupplierRepo;
    private final TransactionDetailRepo detailRepo;
    private final ProductRepo productRepo;

    @Override
    public Transaction create(TransactionRequest rq, String jwt) throws Exception {
        Transaction ett = new Transaction();
        Type type = typeRepo.findTypeByName(rq.getType_name());
        User user = userService.findUserByJwt(jwt);
        Staff staff = staffService.findByUserId(user.getUser_id());
        ett.setContent(rq.getContent());
        ett.setCreated_at(LocalDateTime.now());
        ett.setTotal_quantity(rq.getTotal_quantity());
        ett.setTotal_price(rq.getTotal_price());
        ett.setType_id(type.getType_id());
        ett.setStaff_id(staff.getStaff_id());
        ett.setTransaction_code(generateTransactionCode());
        Transaction save = new Transaction();
        if (rq.getRequest_id() == null) {
            save = transactionRepo.save(ett);
            for (ProductTransRequest item : rq.getProducts()) {
                Optional<Product> product = productRepo.findById(item.getProductId());
                Product get = product.get();
                Transaction_detail detail = new Transaction_detail();
                detail.setNote(item.getNote());
                detail.setQuantity(item.getQuantity());
                detail.setPrice(item.getUnitPrice());
                detail.setProduct_id(item.getProductId());
                detail.setTransaction_id(save.getTransaction_id());
                detail.setStart_quantity(item.getStock());
                detailRepo.save(detail);
                get.setQuantity(get.getQuantity() + detail.getQuantity());
                productRepo.save(get);
            }
        } else {
            ett.setRequest_id(rq.getRequest_id());
            save = transactionRepo.save(ett);
            for (ProductTransRequest item : rq.getProducts()) {
                Optional<Product> product = productRepo.findById(item.getProductId());
                Product get = product.get();
                Transaction_detail detail = new Transaction_detail();
                detail.setNote(item.getNote());
                detail.setQuantity(item.getQuantity());
                detail.setPrice(item.getUnitPrice());
                detail.setProduct_id(item.getProductId());
                detail.setQuantity_request(item.getQuantity_request());
                detail.setTransaction_id(save.getTransaction_id());
                detail.setStart_quantity(item.getStock());
                detailRepo.save(detail);
                get.setQuantity(get.getQuantity() + detail.getQuantity());
                productRepo.save(get);
            }
        }
        Bill_supplier billSupplier = new Bill_supplier();
        billSupplier.setBill_code(rq.getBill_code());
        billSupplier.setSupplier_id(rq.getSupplier_id());
        billSupplier.setCreated_at(LocalDateTime.now());
        billSupplierRepo.save(billSupplier);
        save.setBill_id(billSupplier.getBill_id());

        return transactionRepo.save(save);
    }

    @Override
    public List<Transaction> findAll() {
        return transactionRepo.findAll();
    }

    @Override
    public Transaction findById(Long id) throws Exception {
        Optional<Transaction> find = transactionRepo.findById(id);
        if (find.isPresent()) {
            return find.get();
        }
        throw new Exception("Not found transaction by id " + id);
    }

    @Override
    public List<TransactionStatisticRsp> getStatisticTransaction(String inputType) {
        List<Object[]> results = transactionRepo.findTransactionStatistics(inputType);
        return results.stream()
                .map(this::mapToTransactionStatistic)
                .collect(Collectors.toList());
    }

    @Override
    public List<StatisticRsp> getAllStatistic(LocalDate startDate, LocalDate endDate) {
        List<Object[]> results = transactionRepo.getFullInventoryReport(startDate, endDate);
        return results.stream()
                .map(this::mapToStatistic)
                .collect(Collectors.toList());
    }

    @Override
    public List<StatisticRsp> getAllStatisticByType(LocalDate startDate, LocalDate endDate, String type) {
        List<Object[]> results = transactionRepo.getFullInventoryReportByType(startDate, endDate,type);
        return results.stream()
                .map(this::mapToStatisticByType)
                .collect(Collectors.toList());
    }

    private String generateTransactionCode() {
        // Lấy năm hiện tại
        String currentYear = String.valueOf(java.time.Year.now().getValue()).substring(2); // Lấy 2 số cuối của năm

        // Lấy tất cả giao dịch
        List<Transaction> transactions = transactionRepo.findAll();
        int maxId = 0;

        // Kiểm tra danh sách giao dịch
        for (Transaction p : transactions) {
            String transactionCode = p.getTransaction_code();
            if (transactionCode.startsWith("PN" + currentYear)) { // Kiểm tra xem có cùng năm không
                String idStr = transactionCode.substring(4); // Loại bỏ "NK" + "21"
                int id = Integer.parseInt(idStr);
                if (id > maxId) {
                    maxId = id;
                }
            }
        }

        // Nếu danh sách rỗng hoặc chưa có mã nào cùng năm, tạo mã đầu tiên
        if (maxId == 0) {
            return String.format("PN%s%06d", currentYear, 1); // Bắt đầu từ 001
        }

        // Nếu đã có mã, tăng số thứ tự lên
        return String.format("PN%s%06d", currentYear, maxId + 1);
    }

    private TransactionStatisticRsp mapToTransactionStatistic(Object[] result) {
        Date createDate = (Date) result[0];
        String transactionCode = (String) result[1];
        String watchId = (String) result[2];
        String watchName = (String) result[3];
        Integer price = (Integer) result[4];
        Integer startQty = (Integer) result[5];
        Integer actualQuantity = (Integer) result[6];
        BigInteger type_id = (BigInteger) result[7];
        return new TransactionStatisticRsp(createDate, transactionCode, watchId, watchName, price, startQty, actualQuantity, type_id);
    }

    private StatisticRsp mapToStatistic(Object[] result) {
        String productCode = (String) result[0];      // Mã sản phẩm
        String productName = (String) result[1];      // Tên sản phẩm
        BigDecimal openingQty = (BigDecimal) result[2];      // Số lượng tồn đầu kỳ
        BigDecimal openingValue = (BigDecimal) result[3];     // Giá trị tồn đầu kỳ
        BigDecimal importQty = (BigDecimal) result[4];       // Số lượng nhập trong kỳ
        BigDecimal importValue = (BigDecimal) result[5];      // Giá trị nhập trong kỳ
        BigDecimal exportQty = (BigDecimal) result[6];       // Số lượng xuất trong kỳ
        BigDecimal exportValue = (BigDecimal) result[7];      // Giá trị xuất trong kỳ
        BigDecimal closingQty = (BigDecimal) result[8];      // Số lượng tồn cuối kỳ
        BigDecimal closingValue = (BigDecimal) result[9];     // Giá trị tồn cuối kỳ
        return new StatisticRsp(productCode, productName, openingQty, openingValue, importQty, importValue, exportQty, exportValue, closingQty, closingValue);

    }

    private StatisticRsp mapToStatisticByType(Object[] result) {
        String productCode = (String) result[0];      // Mã sản phẩm
        String productName = (String) result[1];      // Tên sản phẩm
        BigDecimal openingQty = (BigDecimal) result[2];      // Số lượng tồn đầu kỳ
        BigDecimal openingValue = (BigDecimal) result[3];     // Giá trị tồn đầu kỳ
        BigDecimal importQty = (BigDecimal) result[4];       // Số lượng nhập trong kỳ
        BigDecimal importValue = (BigDecimal) result[5];      // Giá trị nhập trong kỳ
        BigDecimal closingQty = (BigDecimal) result[6];      // Số lượng tồn cuối kỳ
        BigDecimal closingValue = (BigDecimal) result[7];     // Giá trị tồn cuối kỳ
        BigDecimal export = BigDecimal.valueOf(0);
        BigDecimal export1 = BigDecimal.valueOf(0);

        return new StatisticRsp(productCode, productName, openingQty, openingValue, importQty, importValue,export ,export1, closingQty, closingValue);

    }



}
