package ptithcm.tttn.function;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RequestStatus {
    WAITING("WAITING"),
    CONFIRM("APPROVED"),
    REJECT("REJECT");

    private final String status;

}
