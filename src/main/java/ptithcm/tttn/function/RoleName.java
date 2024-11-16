package ptithcm.tttn.function;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleName {

    ADMIN("ADMIN"),
    MANAGER("MANAGER"),
    STAFF("STAFF"),
    CUSTOMER("CUSTOMER"),
    WAREHOUSE_STAFF("WAREHOUSE_STAFF"),
    WAREHOUSE_MANAGER("WAREHOUSE_MANAGER"),
    SHIPPER("SHIPPER");

    private final String roleName;

}
