package ptithcm.tttn.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateStatusRequest {

    private int status_index;

    private Boolean is_cancel;

    private Boolean is_delivery;
}
