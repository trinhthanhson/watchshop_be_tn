package ptithcm.tttn.service;

import ptithcm.tttn.entity.Transaction_request;
import ptithcm.tttn.request.TransactionRequest;

import java.util.List;

public interface TransactionRequestService {

    Transaction_request createRequest(TransactionRequest request, String jwt) throws Exception;

    List<Transaction_request> findAll();
}
