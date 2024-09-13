import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

public class JwtUtil {

    private static final String SECRET_KEY = "yourSecretKey";  // Replace with your actual secret key

    // Static method to extract Claims (like user info) from the JWT token
    public static Claims getClaimsFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String jwtToken = (String) authentication.getCredentials();

        // Parse the JWT and extract claims
        return Jwts.parser()
                   .setSigningKey(SECRET_KEY)
                   .parseClaimsJws(jwtToken)
                   .getBody();
    }

    // Static method to get the user role from the JWT token
    public static String getUserRole() {
        Claims claims = getClaimsFromToken();
        return (String) claims.get("role");  // Assuming "role" is a claim in the token
    }

    // Static method to get the user ID from the JWT token
    public static Long getUserId() {
        Claims claims = getClaimsFromToken();
        return Long.parseLong(claims.getSubject());  // Assuming the user ID is stored as the subject
    }
}