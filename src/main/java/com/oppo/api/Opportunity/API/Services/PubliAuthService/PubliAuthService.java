package com.oppo.api.Opportunity.API.Services.PubliAuthService;

import com.oppo.api.Opportunity.API.Models.LoginModel.LoginModel;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;

public interface PubliAuthService {
    ResponseEntity<?> loginUser(LoginModel loginModel);
}
