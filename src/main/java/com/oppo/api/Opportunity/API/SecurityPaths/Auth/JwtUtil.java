package com.oppo.api.Opportunity.API.SecurityPaths.Auth;

import com.oppo.api.Opportunity.API.Entitys.AdmnistradorOppoEntity.AdministradorOppoEntity;
import com.oppo.api.Opportunity.API.Entitys.AlunosEntity.AlunosEntity;
import com.oppo.api.Opportunity.API.Entitys.EscolasEntity.EscolasEntity;
import com.oppo.api.Opportunity.API.Entitys.ProfessoresEntity.ProfessoresEntity;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {

    // Gera uma chave segura para assinatura HS256
    private final SecretKey secret_key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long accessTokenValidity = TimeUnit.HOURS.toMillis(1); // Validade de 1 hora

    private final JwtParser jwtParser;
    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";

    public JwtUtil() {
        this.jwtParser = Jwts.parserBuilder()
                .setSigningKey(secret_key)
                .build();
    }

    // Cria um token JWT com base nos dados da escola

    public String createToken(Object validado) {
        Claims claims = Jwts.claims();

        // Definir o subject com base no tipo do objeto validado
        if (validado instanceof EscolasEntity escola) {
            claims.setSubject(escola.getInformacoesEscola().getCnpj());
            claims.put("id", escola.getId());
            claims.put("senha", escola.getSenha());
            claims.put("nome", escola.getInformacoesEscola().getNome());
            claims.put("role", "ESCOLA");
        } else if (validado instanceof AlunosEntity aluno) {
            claims.setSubject(aluno.getInformacoesPessoais().getCpf());
            claims.put("id", aluno.getId());
            claims.put("senha", aluno.getSenha());
            claims.put("nome", aluno.getInformacoesPessoais().getNome());
            claims.put("role", "ALUNO");
        } else if (validado instanceof ProfessoresEntity professor) {
            claims.setSubject(professor.getInformacoesPessoais().getCpf());
            claims.put("id", professor.getId());
            claims.put("senha", professor.getSenha());
            claims.put("nome", professor.getInformacoesPessoais().getNome());
            claims.put("role", "PROFESSOR");
        } else if (validado instanceof AdministradorOppoEntity admin) {
            claims.setSubject(admin.getInformacoesPessoais().getCpf());
            claims.put("id", admin.getId());
            claims.put("senha", admin.getSenha());
            claims.put("nome", admin.getInformacoesPessoais().getNome());
            claims.put("role", "ADMIN");
        }

        // Data de criação do token e data de expiração
        Date tokenCreateTime = new Date();
        Date tokenExpirationTime = new Date(tokenCreateTime.getTime() + accessTokenValidity);

        // Criar o token JWT
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(tokenCreateTime)  // Data de criação do token
                .setExpiration(tokenExpirationTime)  // Validade do token
                .signWith(secret_key, SignatureAlgorithm.HS256)  // Assinatura com chave secreta e algoritmo HS256
                .compact();
    }


    // Extrai as claims do token JWT
   public Claims parseJwtClaims(String token) {
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
