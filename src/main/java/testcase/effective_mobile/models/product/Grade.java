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
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private ShopUser user;
    @ManyToOne
    private Product product;
    @Column
    private Integer grade;
}
