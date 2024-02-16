package br.com.erudio.security.Jwt;


import br.com.erudio.data.vo.v1.security.TokenVO;
import br.com.erudio.exceptions.InvalidJwtAuthenticationException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey = "secret";

    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMilliseconds = 3600000; // 1h

    // Injetando o UserDetailsService:
    @Autowired
    private UserDetailsService userDetailsService;

    Algorithm algorithm = null;

    @PostConstruct
    protected void init () {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        algorithm = Algorithm.HMAC256(secretKey.getBytes());
    }

    // Implementando a criação do Token:
    public TokenVO createAccessToken(String username, List<String> roles) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        var accessToken = getAccessToken(username, roles, now, validity);
        var refreshToken = getRefreshToken(username, roles, now);
        return new TokenVO(username, true, now, validity, accessToken, refreshToken);
    }


    public TokenVO refreshToken(String refreshToken) {
        if (refreshToken.contains("Bearer ")) refreshToken =
                refreshToken.substring("Bearer ".length());

        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(refreshToken);
        String username = decodedJWT.getSubject();
        List<String> roles = decodedJWT.getClaim("roles").asList(String.class);
        return createAccessToken(username, roles);

    }
        private String getAccessToken(String username, List<String> roles, Date now, Date validity) {
            String issueUrl = ServletUriComponentsBuilder.
                    fromCurrentContextPath().build().toUriString();
            return JWT.create()
                    .withClaim("roles", roles)
                    .withIssuedAt(now)
                    .withExpiresAt(validity)
                    .withSubject(username)
                    .withIssuer(issueUrl)
                    .sign(algorithm)
                    .strip();
        }

        private String getRefreshToken(String username, List<String> roles, Date now) {
            Date validityRefreshToken = new Date(now.getTime() + validityInMilliseconds * 3);
            return JWT.create()
                    .withClaim("roles", roles)
                    .withIssuedAt(now)
                    .withExpiresAt(validityRefreshToken)
                    .withSubject(username)
                    .sign(algorithm)
                    .strip();
        }

        // Implementando a autenticação:
        public Authentication getAuthentication(String token) {
            DecodedJWT decodedJWT = decodedToken(token);
            UserDetails userDetails = this.userDetailsService.
                    loadUserByUsername(decodedJWT.getSubject());
            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        }

        private DecodedJWT decodedToken(String token) {
            Algorithm alg = Algorithm.HMAC256(secretKey.getBytes());
            JWTVerifier verifier = JWT.require(alg).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT;
        }

        // Validando o Token:
        public String resolveToken(HttpServletRequest req) {
            String bearerToken = req.getHeader("Authorization");

            // Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.iagnLj4kDAEYFmkJ_9rPa57vbWLKsNLl7i5d3v28ODw
            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                return bearerToken.substring("Bearer ".length());
            }
            return null;
        }

        public boolean validateToken(String token) {
            DecodedJWT decodedJWT = decodedToken(token);
            try {
                if (decodedJWT.getExpiresAt().before(new Date())) {
                    return false;
                }
                return true;
            } catch (Exception e) {
                throw new InvalidJwtAuthenticationException("Expired or ivalid JWT token!");
            }
        }
    }