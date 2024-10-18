package ptithcm.tttn.config;


import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtTokenProvider {

    private SecretKey key=Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

    // public String generateAccessToken(Authentication auth) {

    // 	String jwt=Jwts.builder()
    // 			.setIssuedAt(new Date())
    // 			.setExpiration(new Date(new Date().getTime()+604800000))
    // 			.claim("username",auth.getName())
    // 			.signWith(key)
    // 			.compact();

    // 	return jwt;
    // }
    public String generateAccessToken(Authentication auth) {
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

        String jwt = Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 604800000))
                .claim("username", auth.getName())
                .claim("authorities",populateAuthorities(auth.getAuthorities()))
                .signWith(key)
                .compact();
        return jwt;
    }

    public String invalidateToken(String token) {
        // Lấy thông tin từ token
        String token1 = token.substring(7);
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token1);

        // // Lấy thời điểm hết hạn từ thông tin của token
        // Date expiration = claimsJws.getBody().getExpiration();

        // // Đặt thời gian hết hạn của token là thời điểm hiện tại
        claimsJws.getBody().setExpiration(new Date());

        // // Tạo token mới với thời gian hết hạn đã hết
        String newToken = Jwts.builder()
                .setClaims(claimsJws.getBody())
                .signWith(key)
                .compact();
        return newToken;
        //return newToken;

    }

    // public String generateRefreshToken(Authentication auth) {

    // 	String jwt=Jwts.builder()
    // 			.setIssuedAt(new Date())
    // 			.setExpiration(new Date(new Date().getTime()+259200000))
    // 			.claim("username",auth.getName())
    // 			.signWith(key)
    // 			.compact();

    // 	return jwt;
    // }

    public String getUsernameFromJwtToken(String jwt) {
        jwt = jwt.substring(7);
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
        String username = String.valueOf(claims.get("username"));
        System.err.println("username get by jwt " + username);
        return username;
    }

    public String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set <String> auths=new HashSet<>();

        for(GrantedAuthority authority:collection) {
            auths.add(authority.getAuthority());
        }
        return String.join(",",auths);
    }


}
