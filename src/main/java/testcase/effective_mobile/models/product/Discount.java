package testcase.effective_mobile.models.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Discount {
    @Id
    private Long id;

    @OneToMany
    private List<Product> discountProducts;

    @Column
    private Double discount;

    @Column
    private Long discountExpirationPeriod;

}
