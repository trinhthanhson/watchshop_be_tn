package ptithcm.tttn.service.impl;

import org.springframework.stereotype.Service;
import ptithcm.tttn.entity.Transaction;
import ptithcm.tttn.repository.TransactionRepo;
import ptithcm.tttn.service.TransactionService;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepo transactionRepo;

    public TransactionServiceImpl(TransactionRepo transactionRepo) {
        this.transactionRepo = transactionRepo;
    }

    @Override
    public Transaction create(Transaction rq) {
        Transaction ett = new Transaction();
        ett.setNote(rq.getNote());
        ett.setContent(rq.getContent());
        ett.setCreated_at(LocalDateTime.now());
        ett.setTotal_quantity(rq.getTotal_quantity());
        ett.setTotal_price(rq.getTotal_price());
        ett.setType_id(rq.getType_id());
        ett.setSupplier_id(rq.getSupplier_id());
        ett.setStaff_id(rq.getStaff_id());
       return transactionRepo.save(ett);
    }

    @Override
    public List<Transaction> findAll() {
        return transactionRepo.findAll();
    }

    @Override
    public Transaction findById(Long id) throws Exception {
        Optional<Transaction> find = transactionRepo.findById(id);
        if(find.isPresent()){
            return find.get();
        }
        throw new Exception("Not found transaction by id " + id);
    }
}
