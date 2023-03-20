package testcase.effective_mobile.models.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import testcase.effective_mobile.models.organization.Organization;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @ManyToOne
    private Organization organization;
    @Column
    private Long price;
    @Column
    private Long quantity;
    @ManyToOne
    private Discount discount;
    @Column
    private String keyWords;
    @Column
    private String parameters;
    @OneToMany
    private List<Grade> grades;
    @OneToMany
    private List<Review> reviewList;

    @Column
    private boolean isDeleted;

}
