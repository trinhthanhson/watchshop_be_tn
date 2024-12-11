package ptithcm.tttn.controller.customer;

import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ptithcm.tttn.entity.Orders;
import ptithcm.tttn.request.OrderRequest;
import ptithcm.tttn.response.ApiResponse;
import ptithcm.tttn.service.OrderService;
import ptithcm.tttn.service.impl.PaypalServiceImpl;
import ptithcm.tttn.service.impl.VNPayServiceImpl;


import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.net.URI;

@RestController
@RequestMapping("/api/customer/payment")
@AllArgsConstructor
public class CustomerPaymentController {


    private final VNPayServiceImpl vnPayService;
    private final OrderService ordersService;
    private final PaypalServiceImpl paypalService;


    @PostMapping("/submit")
    public ResponseEntity<ApiResponse> submitOrder(
            @RequestBody OrderRequest orderRequest,
            HttpServletRequest request, @RequestHeader("Authorization") String jwt) throws Exception {
        // Extract details from the request body
        ApiResponse res = new ApiResponse();
        Orders orders = ordersService.orderPaymentBuyNow(orderRequest, jwt);
        if (orders != null) {
            int orderTotal = orders.getTotal_price();
            String orderInfo = orders.getAddress();
            // Construct base URL
            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            // Generate VNPay URL
            String vnpayUrl = vnPayService.createOrder(orderTotal, orders.getOrder_id(), orderInfo, baseUrl);
            res.setMessage(vnpayUrl);
            res.setStatus(HttpStatus.OK);
            res.setCode(HttpStatus.OK.value());
            return new ResponseEntity<>(res, res.getStatus());

        }
        return ResponseEntity.status(302).header("Location", null).build();

        // Redirect the client to the VNPay URL
    }

    @PostMapping("/paypal")
    public ResponseEntity<ApiResponse> createPayment(@RequestBody OrderRequest orderRequest, @RequestHeader("Authorization") String jwt) throws Exception {
        // Xử lý đặt hàng và tạo thanh toán
        //Orders orders = ordersService.orderBuyCart(orderRequest, jwt);

        // URL chuyển hướng khi hủy hoặc thành công
        String cancelUrl = "http://localhost:5173/404"; // URL hủy thanh toán
        String successUrl = "http://localhost:9999/api/customer/payment/paypal/success"; // URL khi thành công

        // Số tiền cần thanh toán (VND)
        double vndAmount = orderRequest.getTotal_price();
        double conversionRate = 0.00003942; // Tỷ giá chuyển đổi VND sang USD
        double usdAmount = vndAmount * conversionRate;
        Orders orders = ordersService.orderBuyCart(orderRequest, jwt);

        // Tạo Payment thông qua PaypalService
        Payment payment = paypalService.createPayment(
                usdAmount, // Số tiền thanh toán
                "USD", // Đơn vị tiền tệ
                "paypal", // Phương thức thanh toán
                "sale", // Intent
                "Payment Description", // Mô tả thanh toán
                cancelUrl, // URL hủy thanh toán
                successUrl, // URL khi thành công
                orders.getRecipient_name(), // Tên người nhận
                orders.getRecipient_phone(), // Số điện thoại người nhận
                orders.getAddress(), // Địa chỉ người nhận
                orders.getOrder_id(), // ID đơn hàng
                orders.getTotal_quantity() // Số lượng tổng
        );

        // Lấy URL chuyển hướng đến PayPal
        String approvalUrl = payment.getLinks().stream()
                .filter(link -> "approval_url".equals(link.getRel()))
                .findFirst()
                .map(link -> link.getHref())
                .orElseThrow(() -> new PayPalRESTException("Approval URL not found"));

        // Trả về URL để frontend có thể chuyển hướng đến PayPal
        ApiResponse res = new ApiResponse();
        res.setCode(HttpStatus.OK.value());
        res.setStatus(HttpStatus.OK);
        res.setMessage(approvalUrl);

        return ResponseEntity.ok(res); // Trả về kết quả cho frontend
    }


    @GetMapping("/paypal/success")
    public ResponseEntity<?> successPayment(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId) {
        System.out.println("paymentId: " + paymentId);
        System.out.println("payerId: " + payerId);

        try {
            // Thực hiện thanh toán
            Payment payment = paypalService.executePayment(paymentId, payerId);

            // Kiểm tra trạng thái thanh toán
            if (!"approved".equalsIgnoreCase(payment.getState())) {
                throw new PayPalRESTException("Payment not approved");
            }

            // Lấy thông tin đơn hàng từ customField
            String customField = getCustomField(payment);
            Long orderId = Long.parseLong(customField);

            // Đánh dấu thanh toán thành công
            ordersService.paymentSuccess(orderId, true);

            // Chuyển hướng đến trang lịch sử đơn hàng
            String redirectUrl = "http://localhost:5173/orders-history";
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", redirectUrl)
                    .build(); // Trả về HTTP redirect

        } catch (PayPalRESTException e) {
            e.printStackTrace();
            ApiResponse res = new ApiResponse();
            res.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            res.setMessage("Payment failed or canceled");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getCustomField(Payment payment) {
        if (payment.getTransactions() != null && !payment.getTransactions().isEmpty()) {
            // Lấy giao dịch đầu tiên (hoặc duyệt qua từng giao dịch nếu cần)
            Transaction transaction = payment.getTransactions().get(0);

            // Kiểm tra và lấy giá trị "custom"
            if (transaction.getCustom() != null) {
                return transaction.getCustom(); // Trả về giá trị custom
            }
        }
        return null; // Trả về null nếu không tìm thấy
    }

    @PostMapping("/cart")
    public ResponseEntity<ApiResponse> submitOrderCart(
            @RequestBody OrderRequest orderRequest,
            HttpServletRequest request, @RequestHeader("Authorization") String jwt) throws Exception {
        // Extract details from the request body
        ApiResponse res = new ApiResponse();
        Orders orders = ordersService.orderBuyCart(orderRequest, jwt);
        String cancelUrl = "http://localhost:5173/404";
        String successUrl = "http://localhost:5173/orders-history";
        double vndAmount = orders.getTotal_price(); // Số tiền VND
        double conversionRate = 0.00003942; // Tỷ giá chuyển đổi VND sang USD
        double usdAmount = vndAmount * conversionRate;
        Payment payment = paypalService.createPayment(usdAmount, "USD", "paypal", "sale", "Payment Description",
                cancelUrl, successUrl, orders.getRecipient_name(), orders.getRecipient_phone(),
                orders.getAddress(), orders.getOrder_id(), orders.getTotal_quantity());
        res.setCode(HttpStatus.OK.value());
        res.setStatus(HttpStatus.OK);
        res.setMessage(payment.toJSON());
        return new ResponseEntity<>(res, res.getStatus());
        // Redirect the client to the VNPay URL
    }

    @GetMapping("/vnpay-payment")
    public RedirectView handlePaymentReturn(HttpServletRequest request) throws Exception {
        int paymentStatus = vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");
        String order_id = request.getParameter("vnp_TxnRef");
        Long orderId = Long.parseLong(order_id);

        // If payment is successful
        if (paymentStatus == 1) {
            Orders orders = ordersService.updateStatusPayment("4", orderId);
            return new RedirectView("http://localhost:5173/orders-history");
        } else {
            // Handle failure case, possibly redirect to a failure page
            return new RedirectView("http://localhost:5173/payment-failure");
        }
    }

}