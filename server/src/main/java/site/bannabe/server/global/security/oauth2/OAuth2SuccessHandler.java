// OAuth2 성공 핸들러(로그인 성공 시 JWT 토큰 발급)
package site.bannabe.server.global.security.oauth2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import site.bannabe.server.global.jwt.GenerateToken;
import site.bannabe.server.global.jwt.JwtService;
import site.bannabe.server.global.type.ApiResponse;
import site.bannabe.server.global.type.TokenResponse;
import site.bannabe.server.global.utils.JsonUtils;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final JsonUtils jsonUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        
        String email = oAuth2User.getEmail();
        String authorities = String.join(",", oAuth2User.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .toList());

        GenerateToken tokens = jwtService.createJWT(email, authorities);
        
        ApiResponse<TokenResponse> apiResponse = ApiResponse.success("OAuth2 로그인 성공", TokenResponse.create(tokens));
        writeResponse(response, apiResponse);
    }

    private void writeResponse(HttpServletResponse response, ApiResponse<TokenResponse> apiResponse) throws IOException {
        String body = jsonUtils.serializedObjectToJson(apiResponse);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(body);
        response.getWriter().flush();
    }
} 