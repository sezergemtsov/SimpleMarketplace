package testcase.effective_mobile.models.user;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import testcase.effective_mobile.models.product.Product;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Builder
public class Purchasing {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    private ShopUser user;
    @ManyToOne
    private Product product;
    @Column
    private Long quantity;
    @Column
    private Long date;
    @Column
    private Boolean isReturned;

}
