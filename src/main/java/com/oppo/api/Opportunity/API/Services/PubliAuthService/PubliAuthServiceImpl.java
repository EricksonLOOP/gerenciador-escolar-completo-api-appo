package com.oppo.api.Opportunity.API.Services.PubliAuthService;

import com.oppo.api.Opportunity.API.Models.AdmnistradorOppo.AdministradorOppoEntity;
import com.oppo.api.Opportunity.API.Models.Alunos.AlunosEntity;
import com.oppo.api.Opportunity.API.Models.Escolas.EscolasEntity;
import com.oppo.api.Opportunity.API.Models.LoginModel.LoginModel;
import com.oppo.api.Opportunity.API.Models.Professores.ProfessoresEntity;
import com.oppo.api.Opportunity.API.Repositories.AdministradorRepository.AdmRepository;
import com.oppo.api.Opportunity.API.Repositories.AlunosRepository.AlunosRepository;
import com.oppo.api.Opportunity.API.Repositories.EscolasRespository.EscolasRepository;
import com.oppo.api.Opportunity.API.Repositories.ProfessoresRespository.ProfessorRepository;
import com.oppo.api.Opportunity.API.SecurityPaths.Auth.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class PubliAuthServiceImpl implements PubliAuthService {
    final private EscolasRepository escolasRepository;
    final private ProfessorRepository professorRepository;
    final private AlunosRepository alunosRepository;
    final private AdmRepository admRepository;
    @Autowired
    final private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    public PubliAuthServiceImpl(EscolasRepository escolasRepository, ProfessorRepository professorRepository, AlunosRepository alunosRepository, AdmRepository admRepository, AuthenticationManager authenticationManager) {
        this.escolasRepository = escolasRepository;
        this.professorRepository = professorRepository;
        this.alunosRepository = alunosRepository;
        this.admRepository = admRepository;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public ResponseEntity<?> loginUser(LoginModel loginModel) {

        try {
            Object validado = fazerValidacao(loginModel.getUsuario(), loginModel.getSenha());
            Authentication authentication = null;

            if (validado instanceof EscolasEntity escola) {
                authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(escola.getCNPJ(), loginModel.getSenha())
                );
            } else if (validado instanceof AlunosEntity aluno) {
                authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(aluno.getCPF(), loginModel.getSenha())
                );
            } else if (validado instanceof ProfessoresEntity professor) {
                authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(professor.getCPF(), loginModel.getSenha())
                );
            } else if (validado instanceof AdministradorOppoEntity admin) {
                authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(admin.getCPF(), loginModel.getSenha())
                );
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Usuário inválido.");
            }

            if (authentication != null && authentication.isAuthenticated()) {
                String token = jwtUtil.createToken(validado);
                return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                        "message", "Login bem-sucedido",
                        "usuario", validado,
                        "token", token
                ));
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao autenticar.");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
   }
    public Object fazerValidacao(String user, String senha) {
        Optional<EscolasEntity> escola = escolasRepository.findByCNPJ(user);
        Optional<AlunosEntity> aluno = alunosRepository.findByCPF(user);
        Optional<ProfessoresEntity> professor = professorRepository.findByCPF(user);
        Optional<AdministradorOppoEntity> administradorOppo = admRepository.findByCPF(user);
        try {
            if (escola.isPresent() && escola.get().getSenha().equals(senha)) {
                return escola.get();
            }
            if (aluno.isPresent() && aluno.get().getSenha().equals(senha)) {
                return aluno.get();
            }
            if (professor.isPresent() && professor.get().getSenha().equals(senha)) {
                return professor.get();
            }
            if (administradorOppo.isPresent() && administradorOppo.get().getSenha().equals(senha)) {
                return administradorOppo.get();
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
