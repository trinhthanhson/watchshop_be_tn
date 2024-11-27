package ptithcm.tttn.service;

import ptithcm.tttn.entity.Transaction;
import ptithcm.tttn.entity.Type;
import ptithcm.tttn.request.TransactionRequest;
import ptithcm.tttn.response.DataAIRsp;
import ptithcm.tttn.response.RevenueReportRsp;
import ptithcm.tttn.response.StatisticRsp;
import ptithcm.tttn.response.TransactionStatisticRsp;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface TransactionService {

    Transaction create(TransactionRequest rq, String jwt) throws Exception;

    List<Transaction> findAll();

    Transaction findById(Long id) throws Exception;

    List<TransactionStatisticRsp> getStatisticTransaction(String inputType);

    List<StatisticRsp> getAllStatistic(LocalDate startDate, LocalDate endDate);

    List<StatisticRsp> getAllStatisticByType(LocalDate startDate, LocalDate endDate, String type);

    List<DataAIRsp> getDataAI();

    List<RevenueReportRsp> getRevenueReport(Date startDate, Date endDate);

}
