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
    SHIPPER("SHIPPER"),
    SALESPERSON("SALESPERSON"),
    BUSINESS_STAFF("BUSINESS_STAFF"),
    WAREHOUSE_KEEPER("WAREHOUSE_KEEPER"),
    DELIVERY_STAFF("DELIVERY_STAFF"),
    DIRECTOR("DIRECTOR");

    private final String roleName;

}
