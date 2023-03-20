package testcase.effective_mobile.models.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PurchasingDto {
    private long id;
    private Date date;
    private long userId;
    private String username;
    private long productId;
    private String product;
}
