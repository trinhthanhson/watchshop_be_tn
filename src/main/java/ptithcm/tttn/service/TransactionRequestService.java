package ptithcm.tttn.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ptithcm.tttn.entity.Request_detail;
import ptithcm.tttn.entity.Transaction_request;
import ptithcm.tttn.request.ProductTransRequest;
import ptithcm.tttn.request.TransactionRequest;
import ptithcm.tttn.response.RequestNotFullRsp;

import java.util.List;

public interface TransactionRequestService {

    Transaction_request createRequest(TransactionRequest request, String jwt) throws Exception;

    List<Transaction_request> findAll();

    Page<Transaction_request> getAllTransactionRequestByType(String typeName, int page, int limit, String sortField, String sortDirection);

    Transaction_request findById(Long id) throws Exception;

    Transaction_request updateStatus(Transaction_request rq,Long id, String jwt ) throws Exception;

    Transaction_request createRequestExportByOrder(Long id, String jwt) throws Exception;

    void updateRequestDetail(Long id, List<Request_detail> rq) throws Exception;

    List<RequestNotFullRsp> getRequestNotFull(Long rq);

    List<Transaction_request> findTransactionRequestsNotFull();

    Transaction_request updateByDirector(String jwt,Long id) throws Exception;

}
