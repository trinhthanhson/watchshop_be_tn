package ptithcm.tttn.service.impl;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class PaypalServiceImpl {

    private final APIContext apiContext;

    public Payment createPayment(double totalPrice, String currency, String method, String intent, String description,
                                 String cancelUrl, String successUrl, String customerName, String customerPhone,
                                 String customerAddress, Long orderId, int totalQuantity) throws PayPalRESTException {
        try {
            // Kiểm tra các tham số đầu vào
            if (totalPrice <= 0 || currency == null || method == null || intent == null) {
                throw new IllegalArgumentException("Invalid payment parameters");
            }

            // Định nghĩa số tiền giao dịch
            Amount amount = new Amount();
            amount.setCurrency(currency);
            amount.setTotal(String.format(Locale.US, "%.2f", totalPrice));

            // Tạo danh sách sản phẩm
            Item item = new Item();
            item.setName("Order #" + orderId) // Đặt tên đơn hàng
                    .setCurrency(currency)
                    .setPrice(String.format(Locale.US, "%.2f", totalPrice))
                    .setQuantity(String.valueOf(totalQuantity)); // Số lượng tổng của các sản phẩm

            List<Item> items = new ArrayList<>();
            items.add(item);

            // Định nghĩa danh sách sản phẩm và địa chỉ giao hàng
            ItemList itemList = new ItemList();
            itemList.setItems(items);

            if (customerName != null && customerPhone != null && customerAddress != null) {
                ShippingAddress shippingAddress = new ShippingAddress();
                shippingAddress.setRecipientName(customerName);  // Tên khách hàng
                shippingAddress.setPhone(customerPhone);        // Số điện thoại khách hàng
                shippingAddress.setLine1(customerAddress);      // Địa chỉ chính (VD: số nhà, tên đường)
                shippingAddress.setCity("Hanoi");               // Tên thành phố
                shippingAddress.setCountryCode("VN");           // Mã quốc gia (Ví dụ: "VN" cho Việt Nam)
                itemList.setShippingAddress(shippingAddress);
            }

            // Gắn danh sách sản phẩm và chi tiết giao dịch
            Transaction transaction = new Transaction();
            transaction.setDescription(description);
            transaction.setAmount(amount);
            transaction.setItemList(itemList);

            // Sử dụng custom để lưu thông tin bổ sung
            transaction.setCustom( orderId.toString());

            List<Transaction> transactionList = new ArrayList<>();
            transactionList.add(transaction);

            // Cấu hình thông tin người thanh toán
            Payer payer = new Payer();
            payer.setPaymentMethod(method);

            // Cấu hình payment
            Payment payment = new Payment();
            payment.setIntent(intent);
            payment.setPayer(payer);
            payment.setTransactions(transactionList);

            // Cấu hình URL chuyển hướng
            RedirectUrls redirectUrls = new RedirectUrls();
            redirectUrls.setCancelUrl(cancelUrl);
            redirectUrls.setReturnUrl(successUrl);
            payment.setRedirectUrls(redirectUrls);

            // Gửi yêu cầu đến PayPal API
            return payment.create(apiContext);

        } catch (IllegalArgumentException e) {
            throw new PayPalRESTException("Invalid input: " + e.getMessage(), e);
        } catch (PayPalRESTException e) {
            // Log lỗi
            System.err.println("PayPal API error: " + e.getMessage());
            throw e;
        }
    }


    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId); // Sửa thành paymentId
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecution);
    }
}
