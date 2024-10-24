package ptithcm.tttn.function;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleName {

    MANAGER("MANAGER"),
    STAFF("STAFF"),
    CUSTOMER("CUSTOMER"),
    SHIPPER("SHIPPER");

    private final String roleName;

}
