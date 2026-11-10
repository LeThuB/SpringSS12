
package SS12.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {
      //thong bao loi cho frontend neu khong set co the tra ve ma sai
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
       //thong bao du lieu tra ve co dang js
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String error = (String) request.getAttribute("error");
        if (error == null){error = "Token không hợp lệ hoặc thiếu Token ";}

        //object la 1  kieu du lieu chua ca string int boolean
        //tao thong bao loi tra ve frontend
        Map<String,Object> body = new HashMap<>();
        body.put("status","FAIL");
        body.put("message",error);
        body.put("code",HttpServletResponse.SC_UNAUTHORIZED);
        //objectmapper chuyen du lieu java thanh js
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }

}