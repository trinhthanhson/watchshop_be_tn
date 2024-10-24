package ptithcm.tttn.function;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {

    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private final String userStatus;
}
