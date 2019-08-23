package com.xinkokuya.recycle.core.utils;

import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.xinkokuya.recycle.core.JwtSetting;
import com.xinkokuya.recycle.core.auth.JwtUser;

/**
 * JWT工具类，生成令牌 从客户端获取的令牌里面获取相关字段（解密）
 */
@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -3301605591108950415L;

    // 申明部分的属性名
    static final String CLAIM_KEY_USERNAME = "sub";

    static final String CLAIM_KEY_AUDIENCE = "aud";

    static final String CLAIM_KEY_CREATED = "iat";

    // 签名部分的属性名
    static final String AUDIENCE_UNKNOWN = "unknown";

    static final String AUDIENCE_WEB = "web";

    static final String AUDIENCE_MOBILE = "mobile";

    static final String AUDIENCE_TABLET = "tablet";

    private Clock clock = DefaultClock.INSTANCE;

    @Resource
    private RSAUtil rsaUtil;

    @Resource
    private JwtSetting jwtSetting;

    /**
     * 从请求头或请求参数中获取token
     */
    public String getTokenFromRequest(HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader(this.jwtSetting.getHeader());
        if (StringUtils.isEmpty(token)) {
            token = httpRequest.getParameter(this.jwtSetting.getHeader());
        }
        return token;
    }

    // 从得到的令牌里面获得用户名
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public String getAudienceFromToken(String token) {
        return getClaimFromToken(token, Claims::getAudience);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claims == null ? null : claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        final PublicKey secret = this.rsaUtil.loadPemPublicKey(this.jwtSetting.getPublicKey());
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret)
                    .parseClaimsJws(token.replace(this.jwtSetting.getTokenPrefix(), "")).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public Boolean isTokenExpired(String token) {
        try {
            final Date expiration = getExpirationDateFromToken(token);
            return expiration.before(clock.now());
        } catch (Exception e) {
            return true;
        }
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private Boolean ignoreTokenExpiration(String token) {
        String audience = getAudienceFromToken(token);
        return (AUDIENCE_TABLET.equals(audience) || AUDIENCE_MOBILE.equals(audience));
    }

    // 按照传入的用户userDetails和Token的规则生成令牌
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    // 按照传入的用户名生成令牌
    public String sign(String username) {
        Map<String, Object> claims = new HashMap<>();
        String jwtToken = doGenerateToken(claims, username);
        String token = this.jwtSetting.getTokenPrefix() + " " + jwtToken;
        return token;
    }

    // 生成令牌
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);
        final PrivateKey secret = this.rsaUtil.loadPemPrivateKey(this.jwtSetting.getPrivateKey());
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(createdDate)
                .setExpiration(expirationDate).signWith(SignatureAlgorithm.RS256, secret)
                .compressWith(CompressionCodecs.DEFLATE).compact();
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getIssuedAtDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public String refreshToken(String token) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);
        final Claims claims = getAllClaimsFromToken(token);
        final PrivateKey secret = this.rsaUtil.loadPemPrivateKey(this.jwtSetting.getPrivateKey());
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.RS256, secret)
                .compressWith(CompressionCodecs.DEFLATE).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        JwtUser user = (JwtUser) userDetails;
        final String username = getUsernameFromToken(token);
        final Date created = getIssuedAtDateFromToken(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token)
                && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate()));
    }

    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + this.jwtSetting.getExpirationTime() * 1000);
    }
}