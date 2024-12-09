package ptithcm.tttn.controller.inventory;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ptithcm.tttn.entity.Request_detail;
import ptithcm.tttn.entity.Transaction_request;
import ptithcm.tttn.function.MessageError;
import ptithcm.tttn.function.MessageSuccess;
import ptithcm.tttn.function.TypeTrans;
import ptithcm.tttn.request.TransactionRequest;
import ptithcm.tttn.response.*;
import ptithcm.tttn.service.TransactionRequestService;
import ptithcm.tttn.service.impl.TransactionRequestServiceImpl;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/inventory/request")
@AllArgsConstructor
public class StaffRequestController {

    private final TransactionRequestService requestService;


    @GetMapping("/all/import")
    public ResponseEntity<ListEntityResponse<Transaction_request>> getAllTransactionRequestImportHandle(
            @RequestHeader("Authorization") String jwt,
            @RequestParam("page") int page, // Accept page number as a query parameter
            @RequestParam("limit") int limit) { // Accept limit (page size) as a query parameter

        ListEntityResponse<Transaction_request> res = new ListEntityResponse<>();
        try {
            // Create Pageable object using the page and limit parameters
            Pageable pageable = PageRequest.of(page - 1, limit); // Spring Pageable is 0-indexed, so subtract 1 from page

            Page<Transaction_request> etts = requestService.getAllTransactionRequestByType(TypeTrans.IMPORT.getTypeName(), pageable);
            res.setMessage(MessageSuccess.E01.getMessage());
            res.setData(etts.getContent()); // Lấy danh sách từ Page
            res.setCode(HttpStatus.OK.value());
            res.setStatus(HttpStatus.OK);

            // Thêm thông tin phân trang vào response
            res.setTotalPages(etts.getTotalPages()); // Tổng số trang
            res.setTotalElements(etts.getTotalElements()); // Tổng số mục

        } catch (Exception e) {
            res.setMessage(MessageError.E01.getMessage());
            res.setData(null);
            res.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @GetMapping("/all/export")
    public ResponseEntity<ListEntityResponse<Transaction_request>> getAllTransactionRequestExportHandle(@RequestHeader("Authorization") String jwt) {
        ListEntityResponse<Transaction_request> res = new ListEntityResponse<>();
        List<Transaction_request> all = new ArrayList<>();

        try {
            List<Transaction_request> etts = requestService.findAll();
            for (Transaction_request item : etts) {
                if (item.getType_request().getType_name().equals("EXPORT")) {
                    all.add(item);
                }
            }
            res.setMessage(MessageSuccess.E01.getMessage());
            res.setData(all);
            res.setCode(HttpStatus.OK.value());
            res.setStatus(HttpStatus.OK);
        } catch (Exception e) {
            res.setMessage(MessageError.E01.getMessage());
            res.setData(null);
            res.setCode(HttpStatus.OK.value());
            res.setStatus(HttpStatus.OK);
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addTransactionRequestHandle(@RequestHeader("Authorization") String jwt, @RequestBody TransactionRequest rq) {
        ApiResponse res = new ApiResponse();
        try {
            Transaction_request add = requestService.createRequest(rq, jwt);
            res.setMessage("Success");
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
        } catch (Exception e) {
            res.setStatus(HttpStatus.BAD_REQUEST);
            res.setCode(HttpStatus.BAD_REQUEST.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @GetMapping("{id}/get")
    public ResponseEntity<EntityResponse<Transaction_request>> findOrderById(@PathVariable Long id) {

        EntityResponse<Transaction_request> res = new EntityResponse<>();
        try {
            Transaction_request ett = requestService.findById(id);
            res.setData(ett);
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
            res.setMessage(MessageSuccess.E01.getMessage());
        } catch (Exception e) {
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage(MessageError.E01.getMessage() + e.getMessage());
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<EntityResponse<Transaction_request>> cancelOrderByCustomer(@RequestHeader("Authorization") String jwt, @PathVariable Long id, @RequestBody Transaction_request rq) {
        EntityResponse<Transaction_request> res = new EntityResponse<>();
        try {
            Transaction_request ett = requestService.updateStatus(rq, id, jwt);
            res.setData(ett);
            res.setMessage(MessageSuccess.E01.getMessage());
            res.setCode(HttpStatus.OK.value());
            res.setStatus(HttpStatus.OK);
        } catch (Exception e) {
            res.setData(null);
            res.setMessage(MessageError.E01.getMessage() + e.getMessage());
            res.setCode(HttpStatus.CONFLICT.value());
            res.setStatus(HttpStatus.CONFLICT);
            System.err.println(e.getMessage());
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @PostMapping("/{id}/update")
    public ResponseEntity<ApiResponse> updateRequestDetailByManager(@RequestHeader("Authorization") String jwt, @PathVariable Long id, @RequestBody List<Request_detail> rq) {
        ApiResponse res = new ApiResponse();
        try {
            requestService.updateRequestDetail(id, rq);
            res.setMessage("Success");
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
        } catch (Exception e) {
            res.setStatus(HttpStatus.BAD_REQUEST);
            res.setCode(HttpStatus.BAD_REQUEST.value());
            res.setMessage("error " + e.getMessage());
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @GetMapping("{id}/find")
    public ResponseEntity<EntityResponse<RequestDetailRsp>> getRequestDataNotFullHandle(@PathVariable Long id, @RequestHeader("Authorization") String jwt) throws Exception {
        EntityResponse<RequestDetailRsp> res = new EntityResponse<>();
        try {
            Transaction_request request = requestService.findById(id);
            RequestDetailRsp rsp = new RequestDetailRsp();
            rsp.setContent(request.getContent());
            rsp.setRequest_id(request.getRequest_id());
            rsp.setTotal_price(request.getTotal_price());
            rsp.setTotal_quantity(request.getTotal_quantity());
            List<RequestNotFullRsp> ett = requestService.getRequestNotFull(id);
            rsp.setProduct_request(ett);
            res.setData(rsp);
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
            res.setMessage(MessageSuccess.E01.getMessage());
        } catch (Exception e) {
            res.setStatus(HttpStatus.CONFLICT);
            res.setCode(HttpStatus.CONFLICT.value());
            res.setMessage(MessageError.E01.getMessage() + e.getMessage());
        }
        return new ResponseEntity<>(res, res.getStatus());
    }

    @GetMapping("/all/not-full")
    public ResponseEntity<ListEntityResponse<Transaction_request>> getAllTransactionRequestsNotFullHandle(@RequestHeader("Authorization") String jwt) {
        ListEntityResponse<Transaction_request> res = new ListEntityResponse<>();
        try {
            List<Transaction_request> etts = requestService.findTransactionRequestsNotFull();
            res.setMessage(MessageSuccess.E01.getMessage());
            res.setData(etts);
            res.setCode(HttpStatus.OK.value());
            res.setStatus(HttpStatus.OK);
        } catch (Exception e) {
            res.setMessage(MessageError.E01.getMessage());
            res.setData(null);
            res.setCode(HttpStatus.OK.value());
            res.setStatus(HttpStatus.OK);
        }
        return new ResponseEntity<>(res, res.getStatus());
    }
}
