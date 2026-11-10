package SS12.Security;

import SS12.Model.Entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
   @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.access-token-expiration}")
    private long accessToken;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshToken;
public String generateAccessToken(User user){
    Date now = new Date();
    Date accessDate = new Date(now.getTime() + accessToken );
   return Jwts.builder()
           .setSubject(user.getEmail())
           .claim("role",user.getRole().name())
           .claim("type","access")
           .setIssuedAt(now)
           .setExpiration(accessDate)
           .signWith(SignatureAlgorithm.HS512,secret)
           .compact();
}

//tao refresh token

    public String generrateRefreshToken(User user){
    Date now = new Date();
    Date refreshDate = new Date(now.getTime() + refreshToken);
    return Jwts.builder()
            .setSubject(user.getEmail())
            .claim("role",user.getRole().name())
            .setIssuedAt(now)
            .setExpiration(refreshDate)
            .signWith(SignatureAlgorithm.HS512,secret).compact();
    }
    //kiem tra token co hop le va khac null khong
    public boolean validateToken(String token){
    try {
        Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token);
        return true;
    }catch(Exception ex){
        return false;
    }
}
//lay username
    public String getUsernameFromToken(String token){
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}


