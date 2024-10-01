package com.oppo.api.Opportunity.API.SecurityPaths.Auth;

import com.oppo.api.Opportunity.API.DTOs.EscolasDTOs.EscolasDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {

    // Gera uma chave segura para assinatura HS256
    private final SecretKey secret_key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private long accessTokenValidity = TimeUnit.HOURS.toMillis(1); // Validade de 1 hora

    private final JwtParser jwtParser;
    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";

    public JwtUtil() {
        this.jwtParser = Jwts.parserBuilder()
                .setSigningKey(secret_key)
                .build();
    }

    // Cria um token JWT com base nos dados da escola
    public String createToken(EscolasDTO escolasDTO) {
        Claims claims = Jwts.claims().setSubject(escolasDTO.cnpj());
        claims.put("nome", escolasDTO.nome());
        claims.put("role", escolasDTO.role());
        claims.put("cnpj", escolasDTO.cnpj());

        Date tokenCreateTime = new Date();
        Date tokenExpirationTime = new Date(tokenCreateTime.getTime() + accessTokenValidity);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(tokenCreateTime) // Data de criação do token
                .setExpiration(tokenExpirationTime) // Validade do token
                .signWith(secret_key) // Assinatura do token
                .compact();
    }

    // Extrai as claims do token JWT
    private Claims parseJwtClaims(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    // Resolve e valida as claims a partir do request
    public Claims resolveClaims(HttpServletRequest req) {
        try {
            String token = resolveToken(req);
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

    // Extrai o token do cabeçalho da requisição
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    // Valida a data de expiração do token JWT
    public boolean validateClaims(Claims claims) throws AuthenticationException {
        try {
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            throw e;
        }
    }

    // Extrai o usuário (CNPJ) a partir das claims
    public String getUser(Claims claims) {
        return claims.getSubject();
    }

    // Extrai as roles do token JWT
    private List<String> getRoles(Claims claims) {
        return (List<String>) claims.get("role");
    }
}
