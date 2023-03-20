package testcase.effective_mobile.models.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import testcase.effective_mobile.models.user.ShopUser;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Review {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Product product;
    @ManyToOne
    private ShopUser user;
    @Column
    private String review;
    @Column
    private Boolean isDeleted;

}
