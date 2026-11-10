package SS12.Security;

import SS12.Model.Entity.User;
import io.jsonwebtoken.Jwts;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPrincipal implements UserDetails {
    private User user;
    private Collection<? extends GrantedAuthority> authorities;
    @Override
    //lay cac quyen cua nguoi dung
    public Collection<?extends GrantedAuthority> getAuthorities() {
   return authorities;
    }
@Override
    public String getPassword(){
        return user.getPassword();
}
@Override
    public String getUsername(){return user.getEmail();}
//tai khoan co duoc kich hoat hay khong
    @Override
    public boolean isEnabled(){return user.isEnabled();}


}
