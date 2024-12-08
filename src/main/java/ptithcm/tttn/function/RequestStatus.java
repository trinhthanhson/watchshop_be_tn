package ptithcm.tttn.function;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RequestStatus {
    WAITING("WAITING"),
    ACCEPT("ACCEPT"),
    NOT_FULL("NOTFULL"),
    FULL("FULL"),
    REJECT("REJECT");

    private final String status;

}
