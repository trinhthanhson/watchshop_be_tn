package ptithcm.tttn.controller.customer;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ptithcm.tttn.response.ApiResponse;
import ptithcm.tttn.service.impl.PaypalServiceImpl;

@RestController
@RequestMapping("/api/customer/paypal")
@RequiredArgsConstructor
public class CustomerPaypalController {

    private final PaypalServiceImpl paypalService;

    @GetMapping("/create")
    public ResponseEntity<ApiResponse> createPaypal() throws PayPalRESTException {
        String cancelUrl = "";
        String successUrl = "http://localhost:5173/orders-history";
        Payment payment = paypalService.createPayment(10.0,"VND","paypal","sale","payment",cancelUrl,successUrl);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
