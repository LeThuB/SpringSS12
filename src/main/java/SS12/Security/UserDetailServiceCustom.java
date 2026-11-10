package SS12.Security;

import SS12.Model.Entity.User;
import SS12.Repository.UserRepository;
import org.hibernate.sql.ast.tree.expression.Collation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserDetailServiceCustom implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserPrincipal loadUserByUsername(String email)
        throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(()->
                new UsernameNotFoundException("Khong tim thay nguoi dung co email : "+ email));
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
        return new UserPrincipal(user,authorities);
    }


}
