package testcase.effective_mobile.models.organization;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import testcase.effective_mobile.models.user.ShopUser;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String logo;
    @Column
    private Boolean isDeleted;
    @Column
    private Boolean isFrozen;
    @ManyToOne
    private ShopUser creator;
    @Column
    private Boolean registered;
    @Column
    private Double balance;

}
