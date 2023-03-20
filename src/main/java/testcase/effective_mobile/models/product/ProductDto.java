package testcase.effective_mobile.models.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductDto {
    private long id;
    private String name;
    private String description;
    private String organization;
    private long price;
    private long quantity;
    private Discount discount;
    private String keyWords;
    private String parameters;
    private Double grade;
    private List<Review> reviewList;
}
