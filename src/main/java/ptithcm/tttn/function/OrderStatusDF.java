package ptithcm.tttn.function;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatusDF {
    Waiting("0"),
    Confirm("1"),
    Warehouse_Confirm("2"),
    Shipping("3"),
    WaitingPayment("4"),
    Payment("5"),
    Delivered("6"),
    Canceled("7");

    private final String orderStatus;
}
