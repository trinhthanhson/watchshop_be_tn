package ptithcm.tttn.service;

import ptithcm.tttn.entity.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction create(Transaction rq);

    List<Transaction> findAll();

    Transaction findById(Long id) throws Exception;
}
