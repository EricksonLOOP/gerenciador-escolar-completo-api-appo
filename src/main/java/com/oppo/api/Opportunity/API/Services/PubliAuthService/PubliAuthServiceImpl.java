package com.oppo.api.Opportunity.API.Services.PubliAuthService;

import com.oppo.api.Opportunity.API.Entitys.AdmnistradorOppoEntity.AdministradorOppoEntity;
import com.oppo.api.Opportunity.API.Entitys.AlunosEntity.AlunosEntity;
import com.oppo.api.Opportunity.API.Entitys.EscolasEntity.EscolasEntity;
import com.oppo.api.Opportunity.API.Models.LoginModel.LoginModel;
import com.oppo.api.Opportunity.API.Entitys.ProfessoresEntity.ProfessoresEntity;
import com.oppo.api.Opportunity.API.Repositories.AdministradorRepository.AdmRepository;
import com.oppo.api.Opportunity.API.Repositories.AlunosRepository.AlunosRepository;
import com.oppo.api.Opportunity.API.Repositories.EscolasRespository.EscolasRepository;
import com.oppo.api.Opportunity.API.Repositories.ProfessoresRespository.ProfessorRepository;
import com.oppo.api.Opportunity.API.SecurityPaths.Auth.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
            Authentication authentication;

            if (validado instanceof EscolasEntity escola) {
                authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(escola.getInformacoesEscola().getCnpj(), loginModel.getSenha())
                );
            } else if (validado instanceof AlunosEntity aluno) {
                authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(aluno.getInformacoesPessoais().getCpf(), loginModel.getSenha())
                );
            } else if (validado instanceof ProfessoresEntity professor) {
                authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(professor.getInformacoesPessoais().getCpf(), loginModel.getSenha())
                );
            } else if (validado instanceof AdministradorOppoEntity admin) {
                authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(admin.getInformacoesPessoais().getCpf(), loginModel.getSenha())
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
        Optional<EscolasEntity> escola = escolasRepository.findByInformacoesEscola_Cnpj(user);
        Optional<AlunosEntity> aluno = alunosRepository.findByInformacoesPessoais_Cpf(user);
        Optional<ProfessoresEntity> professor = professorRepository.findByInformacoesPessoais_Cpf(user);
        Optional<AdministradorOppoEntity> administradorOppo = admRepository.findByInformacoesPessoais_Cpf(user);
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
