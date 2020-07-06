package com.example.employeebe.config.tokenprovider;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;


/**
 * Created by fan.jin on 2016-10-19.
 */

@Component
public class TokenHelper {

    @Value("${app.name}")
    private String APP_NAME;

    @Value("${jwt.secret}")
    public String SECRET;

    @Value("${jwt.expires_in}")
    private int EXPIRES_IN;

    @Value("${jwt.mobile_expires_in}")
    private int MOBILE_EXPIRES_IN;

    @Value("${jwt.header}")
    private String AUTH_HEADER;

    static final String AUDIENCE_UNKNOWN = "unknown";
    static final String AUDIENCE_WEB = "web";
    static final String AUDIENCE_MOBILE = "mobile";
    static final String AUDIENCE_TABLET = "tablet";

//    @Autowired
//    TimeProvider timeProvider;

    @Autowired
    private JwtConfig jwtConfig;


    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Date getIssuedAtDateFromToken(String token) {
        Date issueAt;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            issueAt = claims.getIssuedAt();
        } catch (Exception e) {
            issueAt = null;
        }
        return issueAt;
    }

    public String getAudienceFromToken(String token) {
        String audience;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            audience = claims.getAudience();
        } catch (Exception e) {
            audience = null;
        }
        return audience;
    }

    public String generateToken(String username, List<GrantedAuthority> grantedAuthorityList){

        Long now = System.currentTimeMillis();

        return Jwts.builder()
                .setSubject(username)
                // Convert to list of strings.
                // This is important because it affects the way we get them back in the Gateway.
                .claim("authorities", grantedAuthorityList)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + EXPIRES_IN * 1000))  // in milliseconds
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes())
                .compact();
    }

//    public String refreshToken(String token, Device device) {
//        String refreshedToken;
//        Date a = timeProvider.now();
//        try {
//            final Claims claims = this.getAllClaimsFromToken(token);
//            claims.setIssuedAt(a);
//            refreshedToken = Jwts.builder()
//                .setClaims(claims)
//                .setExpiration(generateExpirationDate(device))
//                .signWith( SIGNATURE_ALGORITHM, SECRET )
//                .compact();
//        } catch (Exception e) {
//            refreshedToken = null;
//        }
//        return refreshedToken;
//    }

//    public String generateToken(String username, Device device) {
//        String audience = generateAudience(device);
//        return Jwts.builder()
//                .setIssuer( APP_NAME )
//                .setSubject(username)
//                .setAudience(audience)
//                .setIssuedAt(timeProvider.now())
//                .setExpiration(generateExpirationDate(device))
//                .signWith( SIGNATURE_ALGORITHM, SECRET )
//                .compact();
//    }

//    private String generateAudience(Device device) {
//        String audience = AUDIENCE_UNKNOWN;
//        if (device.isNormal()) {
//            audience = AUDIENCE_WEB;
//        } else if (device.isTablet()) {
//            audience = AUDIENCE_TABLET;
//        } else if (device.isMobile()) {
//            audience = AUDIENCE_MOBILE;
//        }
//        return audience;
//    }

    private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getSecret().getBytes())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

//    private Date generateExpirationDate(Device device) {
//        long expiresIn = device.isTablet() || device.isMobile() ? MOBILE_EXPIRES_IN : EXPIRES_IN;
//        return new Date(timeProvider.now().getTime() + expiresIn * 1000);
//    }

//    public int getExpiredIn(Device device) {
//        return device.isMobile() || device.isTablet() ? MOBILE_EXPIRES_IN : EXPIRES_IN;
//    }

/*
       commented here

public Boolean validateToken(String token, UserState userDetails) {
        UserState user = (UserState) userDetails;
        final String username = getUsernameFromToken(token);
        final Date created = getIssuedAtDateFromToken(token);
        return (
                username != null &&
                username.equals(userDetails.getUserId())
        );
    }*/



    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    public String getToken( HttpServletRequest request ) {
        /**
         *  Getting the token from Authentication header
         *  e.g Bearer your_token
         */
        String authHeader = getAuthHeaderFromHeader( request );
        if ( authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return null;
    }

    public String getAuthHeaderFromHeader( HttpServletRequest request ) {

        Enumeration<String> headers= request.getHeaders(AUTH_HEADER);
        String header=null;
        if(headers.hasMoreElements()){
            header=headers.nextElement();
        }
        return header;
    }

}
