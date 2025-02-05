package ptithcm.tttn.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import ptithcm.tttn.entity.Transaction;
import ptithcm.tttn.entity.Transaction_request;
import ptithcm.tttn.entity.Type;
import ptithcm.tttn.request.TransactionRequest;
import ptithcm.tttn.response.*;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface TransactionService {

    Transaction create(TransactionRequest rq, String jwt) throws Exception;

    List<Transaction> findAll();

    BigInteger existsByRequestId(@Param("requestId") Long requestId);

    Transaction findById(Long id) throws Exception;

    List<TransactionStatisticRsp> getStatisticTransaction(String inputType);

    List<StatisticRsp> getAllStatistic(LocalDate startDate, LocalDate endDate);

    List<StatisticRsp> getAllStatisticByType(LocalDate startDate, LocalDate endDate, String type);

    List<DataAIRsp> getDataAI();

    List<RevenueReportRsp> getRevenueReport(Date startDate, Date endDate);

    Transaction createExport(Long request_id,String jwt) throws Exception;

    List<GetDataAiTransaction> getDataAiTransaction(int quantity);

     Page<Transaction> getAllTransactionByType(String typeName, int page, int limit, String sortField, String sortDirection);


}
