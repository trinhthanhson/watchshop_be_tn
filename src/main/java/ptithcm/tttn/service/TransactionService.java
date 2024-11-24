package ptithcm.tttn.service;

import ptithcm.tttn.entity.Transaction;
import ptithcm.tttn.request.TransactionRequest;
import ptithcm.tttn.response.TransactionStatisticRsp;

import java.util.List;

public interface TransactionService {

    Transaction create(TransactionRequest rq, String jwt) throws Exception;

    List<Transaction> findAll();

    Transaction findById(Long id) throws Exception;

    List<TransactionStatisticRsp> getStatisticTransaction(String inputType);
}
