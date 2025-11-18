package vn.vti.dtn2504.usermanager.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;
import vn.vti.dtn2504.usermanager.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class UserTokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {
    private final UserRepository userRepository;

    @Override
    public void customize(JwtEncodingContext context) {
        if (!OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
            return;
        }

        String username = context.getPrincipal().getName();
        userRepository.findByUsername(username)
                .ifPresent(user -> {
                    context.getClaims().claim("user_id", user.getId());
                    context.getClaims().claim("username", user.getUsername());
                    context.getClaims().claim("email", user.getEmail());
                });
    }
}
