package SS12.Security;

import jakarta.servlet.FilterChain;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilterChain extends OncePerRequestFilter {
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserDetailServiceCustom userDetailServiceCustom;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException{

        String token = getJwtFromRequest(request);


//            String token = getJwtFromRequest(request);
           //kiem tra token phai ton tai khng rong va hop le
            if(token !=null && jwtProvider.validateToken(token)){
                String username = jwtProvider.getUsernameFromToken(token);
              //lay thong tin nguoi dung tu database
                UserDetails userDetails = userDetailServiceCustom.loadUserByUsername(username);
              //tao doi tuong dai dien cho nguo dung da duoc xac thuc
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                  userDetails,null,userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }
//cho filter chay tiep
filterChain.doFilter(request,response);

    }
    public String getJwtFromRequest(HttpServletRequest request){
//lay gia tri cua header
        String bearertoken = request.getHeader("Authorization");
    //kiem tra header co rong khong va phai bat dau bang bearer
        if(bearertoken != null && bearertoken.startsWith("Bearer ")){
            return bearertoken.substring(7);
        }
        return null;
    }
}
