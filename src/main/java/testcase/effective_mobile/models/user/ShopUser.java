package testcase.effective_mobile.models.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import testcase.effective_mobile.models.organization.Organization;
import testcase.effective_mobile.models.product.Grade;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class ShopUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nameOfUser;
    @Column(nullable = false)
    private String passwordOfUser;
    @Column(nullable = false)
    private String emailOfUser;
    @Column(nullable = false)
    private Double balanceOfUser;
    @OneToMany
    private List<Organization> organizationList;
    @Column
    private Boolean sellingRight;
    @Column
    private Boolean isBanned;
    @Column(nullable = false)
    private String systemRole;
    @OneToMany
    private List<Grade> grades;

}
