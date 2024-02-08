package com.example.passengerbackend.Auth;

import com.example.passengerbackend.Entity.Passenger;
import io.jsonwebtoken.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class JWTUtil {

    private final String secret_key = "mysecretkey";
    private long accessTokenValidity = 60*60*1000;

    //used for parsing JWT claims
    private final JwtParser jwtParser;

    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";

    public JWTUtil(){
        this.jwtParser = Jwts.parser().setSigningKey(secret_key);
    }

    //takes an User object , creates a Claims object from user data and builds a jwt token with Jwts.builder()
    public String createToken(Passenger passenger) {
        Claims claims = Jwts.claims().setSubject(passenger.getEmail());
        claims.put("firstName",passenger.getFirstName());
        claims.put("lastName",passenger.getLastName());
        Date tokenCreateTime = new Date();
        Date tokenValidity = new Date(tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(accessTokenValidity));
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(tokenValidity)
                .signWith(SignatureAlgorithm.HS256, secret_key)
                .compact();
    }

    private Claims parseJwtClaims(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }


    //takes an HttpServletRequest object as parameter and resolve Claims object from Bearer token in the request header
    //It will through an exception if no such token is present in request header or the token is expired or invalid.
    public Claims resolveClaims(HttpServletRequest req) {
        try {
            String token = resolveToken(req); //Retrieves the token from the request.
            if (token != null) {
                return parseJwtClaims(token);
            }
            return null;
        } catch (ExpiredJwtException ex) {
            req.setAttribute("expired", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            req.setAttribute("invalid", ex.getMessage());
            throw ex;
        }
    }


    //Retrieves the token from the request.
    public String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public boolean validateClaims(Claims claims) throws AuthenticationException {
        try {
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            throw e;
        }
    }

    public String getEmail(Claims claims) {
        return claims.getSubject();
    }

    private List<String> getRoles(Claims claims) {
        return (List<String>) claims.get("roles");
    }
}
