package vn.vti.dtn2504.mallservice.security;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public record AuthenticatedUser(Long userId, String username) {

    public static AuthenticatedUser from(JwtAuthenticationToken authentication) {
        Jwt jwt = authentication.getToken();
        Long userId = extractUserId(jwt);
        String username = extractUsername(jwt, authentication);
        return new AuthenticatedUser(userId, username);
    }

    private static Long extractUserId(Jwt jwt) {
        Object userIdClaim = jwt.getClaims().get("user_id");
        if (userIdClaim == null) {
            return null;
        }
        try {
            return Long.valueOf(String.valueOf(userIdClaim));
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private static String extractUsername(Jwt jwt, JwtAuthenticationToken authentication) {
        String username = jwt.getClaimAsString("preferred_username");
        if (username == null) {
            username = jwt.getClaimAsString("username");
        }
        if (username == null) {
            username = authentication.getName();
        }
        return username;
    }
}
