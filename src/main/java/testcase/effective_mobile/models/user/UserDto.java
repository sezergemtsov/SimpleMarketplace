package testcase.effective_mobile.models.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import testcase.effective_mobile.models.organization.Organization;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private double balance;
    private List<Organization> organizationList;
    private boolean sellingRight;
    private boolean isBanned;
}
