package ptithcm.tttn.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Data
public class RequestDetailRsp {
    private String content;
    private Long request_id;
    private int total_price;
    private int  total_quantity;
    private List<RequestNotFullRsp> product_request;

}
