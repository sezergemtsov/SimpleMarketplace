package testcase.effective_mobile.models.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReturningDto {
    private Long id;
    private long userId;
    private String username;
    private long productId;
    private String product;
    private Date date;
}
