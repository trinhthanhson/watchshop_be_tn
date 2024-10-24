package ptithcm.tttn.function;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    Waiting("0"),
    Confirm("1"),
    Shipping("2"),
    WaitingPayment("3"),
    Payment("4"),
    Delivered("5"),
    Canceled("6");

    private final String orderStatus;
}
