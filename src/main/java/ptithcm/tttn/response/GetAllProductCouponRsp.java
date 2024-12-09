package ptithcm.tttn.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class GetAllProductCouponRsp {
    private String product_id;
    private String band_material;
    private String band_width;
    private BigInteger brand_id;
    private String case_diameter;
    private String case_material;
    private String case_thickness;
    private BigInteger category_id;
    private String color;
    private String detail;
    private String dial_type;
    private String func;
    private String gender;
    private String machine_movement;
    private String model;
    private String product_name;
    private Integer quantity;
    private Integer quantityProduct;
    private String series;
    private String water_resistance;
    private String image;
    private BigInteger created_by;
    private BigInteger updated_by;
    private String status;
    private Timestamp created_at;
    private Timestamp updated_at;
    private String brand_name;
    private String category_name;
    private String created_by_name;
    private String updated_by_name;
    private Double current_price;
    private Double discounted_price;
}
