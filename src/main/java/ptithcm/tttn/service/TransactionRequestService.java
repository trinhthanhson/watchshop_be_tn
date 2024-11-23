package ptithcm.tttn.service;

import ptithcm.tttn.entity.Request_detail;
import ptithcm.tttn.entity.Transaction_request;
import ptithcm.tttn.request.ProductTransRequest;
import ptithcm.tttn.request.TransactionRequest;

import java.util.List;

public interface TransactionRequestService {

    Transaction_request createRequest(TransactionRequest request, String jwt) throws Exception;

    List<Transaction_request> findAll();

    Transaction_request findById(Long id) throws Exception;

    Transaction_request updateStatus(Transaction_request rq,Long id, String jwt ) throws Exception;

    Transaction_request createRequestExportByOrder(Long id, String jwt) throws Exception;

    void updateRequestDetail(Long id, List<Request_detail> rq) throws Exception;
}
