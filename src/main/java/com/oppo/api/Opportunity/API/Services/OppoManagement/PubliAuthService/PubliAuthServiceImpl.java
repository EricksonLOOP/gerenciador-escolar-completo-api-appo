package com.oppo.api.Opportunity.API.Services.OppoManagement.PubliAuthService;

import com.oppo.api.Opportunity.API.Entitys.AdmnistradorOppoEntity.AdministradorOppoEntity;
import com.oppo.api.Opportunity.API.Entitys.AlunosEntity.AlunosEntity;
import com.oppo.api.Opportunity.API.Entitys.EscolasEntity.EscolasEntity;
import com.oppo.api.Opportunity.API.Models.CriarUsuario.CriarUsuarioModel;
import com.oppo.api.Opportunity.API.Models.InformacoesPessoaisModel.InformacoesPessoaisModel;
import com.oppo.api.Opportunity.API.Models.LoginModel.LoginModel;
import com.oppo.api.Opportunity.API.Entitys.ProfessoresEntity.ProfessoresEntity;
import com.oppo.api.Opportunity.API.Repositories.AdministradorRepository.AdmRepository;
import com.oppo.api.Opportunity.API.Repositories.AlunosRepository.AlunosRepository;
import com.oppo.api.Opportunity.API.Repositories.EscolasRespository.EscolasRepository;
import com.oppo.api.Opportunity.API.Repositories.ProfessoresRespository.ProfessorRepository;
import com.oppo.api.Opportunity.API.SecurityPaths.Auth.JwtUtil;
import com.oppo.api.Opportunity.API.Services.OppoManagement.ValidacoesServices.ValidacoesService;
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
    final private ValidacoesService validacoesService;
    @Autowired
    private JwtUtil jwtUtil;

    public PubliAuthServiceImpl(EscolasRepository escolasRepository, ProfessorRepository professorRepository, AlunosRepository alunosRepository, AdmRepository admRepository, AuthenticationManager authenticationManager, ValidacoesService validacoesService) {
        this.escolasRepository = escolasRepository;
        this.professorRepository = professorRepository;
        this.alunosRepository = alunosRepository;
        this.admRepository = admRepository;
        this.authenticationManager = authenticationManager;
        this.validacoesService = validacoesService;
    }

    @Override
    public ResponseEntity<?> loginUser(LoginModel loginModel) {

        try {
            Object validado = fazerValidacao(loginModel.getUsuario(), loginModel.getSenha());
            Authentication authentication = null;

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

    @Override
    public ResponseEntity<?> createUser(CriarUsuarioModel criarUsuarioModel) {
        try {
            if (validacoesService.verificarSeContaExistente(criarUsuarioModel.getCpf())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Já existe uma conta com este email.");
            }
            if (!validacoesService.validarcpf(criarUsuarioModel.getCpf())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Formato de CPF inválido ou CPF inexistente");
            }
            AlunosEntity newAluno = AlunosEntity.builder()
                    .senha(criarUsuarioModel.getPassword())
                    .informacoesPessoais( InformacoesPessoaisModel.builder()
                            .cpf(criarUsuarioModel.getCpf())
                            .build())
                    .build();
            alunosRepository.save(newAluno);
            return ResponseEntity.status(HttpStatus.OK).body("Usuário criado com sucesso.");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    public Object fazerValidacao(String user, String senha) {
        System.out.println("VALIDANDO");
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
