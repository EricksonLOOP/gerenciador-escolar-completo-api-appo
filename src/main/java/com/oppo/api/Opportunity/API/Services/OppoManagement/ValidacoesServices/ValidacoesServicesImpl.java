package com.oppo.api.Opportunity.API.Services.OppoManagement.ValidacoesServices;

import com.oppo.api.Opportunity.API.Entitys.AdmnistradorOppoEntity.AdministradorOppoEntity;
import com.oppo.api.Opportunity.API.Entitys.AlunosEntity.AlunosEntity;
import com.oppo.api.Opportunity.API.Entitys.EscolasEntity.EscolasEntity;
import com.oppo.api.Opportunity.API.Entitys.ProfessoresEntity.ProfessoresEntity;
import com.oppo.api.Opportunity.API.Repositories.AdministradorRepository.AdmRepository;
import com.oppo.api.Opportunity.API.Repositories.AlunosRepository.AlunosRepository;
import com.oppo.api.Opportunity.API.Repositories.EscolasRespository.EscolasRepository;
import com.oppo.api.Opportunity.API.Repositories.ProfessoresRespository.ProfessorRepository;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class ValidacoesServicesImpl implements ValidacoesService {
    final private EscolasRepository escolasRepository;
    final private ProfessorRepository professorRepository;
    final private AlunosRepository alunosRepository;
    final private AdmRepository admRepository;

    public ValidacoesServicesImpl(EscolasRepository escolasRepository, ProfessorRepository professorRepository, AlunosRepository alunosRepository, AdmRepository admRepository) {
        this.escolasRepository = escolasRepository;
        this.professorRepository = professorRepository;
        this.alunosRepository = alunosRepository;
        this.admRepository = admRepository;
    }


    @Override
    public Boolean validarcpf(String cpf) throws Exception {
        URL obj = new URL("https://www.4devs.com.br/ferramentas_online.php");
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            String urlParameters = "acao=validar_cpf&txt_cpf=" + cpf;
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = urlParameters.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    String responseBody = response.toString();
                    System.out.println("Response: " + responseBody);
                    if (responseBody.contains("Verdadeiro")) {
                        return true;
                    } else if (responseBody.contains("Falso")) {
                        return false;
                    }
                }
            } else {
                System.out.println("Request failed: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }


    @Override
    public Boolean validarcnpj(String cnpj) throws Exception {
        URL obj = new URL("https://www.4devs.com.br/ferramentas_online.php");
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            String urlParameters = "acao=validar_cnpj&txt_cnpj=" + cnpj;
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = urlParameters.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {

                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                    String responseBody = response.toString();
                    System.out.println("Response: " + responseBody);
                    if (responseBody.contains("Verdadeiro")) {
                        return true;
                    } else if (responseBody.contains("Falso")) {
                        return false;
                    }
                }
            } else {
                System.out.println("Request failed: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return  null;
    }

    @Override
    public Boolean verificarSeContaExistente(String email) throws Exception {
        Optional<EscolasEntity> escola = escolasRepository.findByInformacoesEscola_Cnpj(email);
        Optional<AlunosEntity> aluno = alunosRepository.findByInformacoesPessoais_Cpf(email);
        Optional<ProfessoresEntity> professor = professorRepository.findByInformacoesPessoais_Cpf(email);
        Optional<AdministradorOppoEntity> administradorOppo = admRepository.findByInformacoesPessoais_Cpf(email);
        if (escola.isPresent() |aluno.isPresent()| professor.isPresent() | administradorOppo.isPresent()  ){
            return true;
        }
        return false;
    }
}
