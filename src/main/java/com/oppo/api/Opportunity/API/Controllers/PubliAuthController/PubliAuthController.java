package com.oppo.api.Opportunity.API.Controllers.PubliAuthController;

import com.oppo.api.Opportunity.API.Models.LoginModel.LoginModel;
import com.oppo.api.Opportunity.API.Services.PubliAuthService.PubliAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class PubliAuthController {
    @Autowired
    private PubliAuthService publiAuthService;
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginModel loginModel){
        return publiAuthService.loginUser(loginModel);
    }

}
