package testcase.effective_mobile.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;
import testcase.effective_mobile.models.user.ShopUser;
import testcase.effective_mobile.repositories.UsersRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class SecurityConfig {

    final UsersRepository usersRepository;

    public SecurityConfig(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Bean
    public UserDetailsService users() {

        @SuppressWarnings("deprecation")
        User.UserBuilder users = User.withDefaultPasswordEncoder();

        List<ShopUser> userList = usersRepository.findAll();
        List<UserDetails> userDetails = new ArrayList<>();
        userList.forEach(x -> userDetails.add(
                users
                        .username(x.getNameOfUser())
                        .password(x.getPasswordOfUser())
                        .authorities(x.getSystemRole())
                        .build()
        ));
        return new InMemoryUserDetailsManager(userDetails);
    }
}
