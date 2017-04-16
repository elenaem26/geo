package geo.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

import geo.domain.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import static java.util.Collections.emptyList;

/**
 * Created by ehm on 15.04.2017.
 */
class TokenAuthenticationService {
    //TODO move to properties

    static final long EXPIRATIONTIME = 864_000_000; // 10 days
    static final String SECRET = "ThisIsASecret";
    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";

    static void addAuthentication(HttpServletResponse res, String username, Collection<? extends GrantedAuthority> authorities) {
        String JWT = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .claim("roles", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .compact();//TODO put roles to constant
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
    }

    static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.'
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody();
            String user = claims.getSubject();
            Object roles = claims.get("roles");
            ArrayList<GrantedAuthority> authorities = new ArrayList<>();
            if (roles != null) {
                List<String> stringAuthorities = (ArrayList<String>) roles;
                for (String role : stringAuthorities) {
                    authorities.add(new SimpleGrantedAuthority(role));
                }
            }
            return user != null ?
                    new UsernamePasswordAuthenticationToken(user, null, authorities) :
                    null;
        }
        return null;
    }
}
