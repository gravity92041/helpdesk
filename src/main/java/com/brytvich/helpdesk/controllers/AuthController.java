package com.brytvich.helpdesk.controllers;

import com.brytvich.helpdesk.dto.AuthDTO;
import com.brytvich.helpdesk.dto.UserDTO;
import com.brytvich.helpdesk.models.User;
import com.brytvich.helpdesk.security.JWTUtil;
import com.brytvich.helpdesk.services.CustomUserDetailsService;
import com.brytvich.helpdesk.services.RegistrationService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/api/auth")
@CrossOrigin
public class AuthController {
    private final RegistrationService registrationService;

    private final CustomUserDetailsService customUserDetailsService;

    private final AuthenticationManager authenticationManager;

    private final JWTUtil jwtUtil;

    private final ModelMapper mapper;
    @Autowired
    public AuthController(RegistrationService registrationService, AuthenticationManager authenticationManager, JWTUtil jwtUtil, ModelMapper mapper,
                          CustomUserDetailsService customUserDetailsService) {
        this.registrationService = registrationService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.mapper = mapper;
        this.customUserDetailsService = customUserDetailsService;
    }

//    @GetMapping("/login")
//    public String login

    //ПЕРЕПИСАТЬ МЕТОДЫ ПОД HTTPENTITY
    @PostMapping("/registration")
    public Map<String,String> performRegistration (@RequestBody @Valid UserDTO userDTO,
                                                   BindingResult bindingResult){
        User user = convertToUser(userDTO);

        if (bindingResult.hasErrors()){
            return Map.of("message","error");
        }
        registrationService.register(user);
//        String token  = jwtUtil.generateToken(user.getUsername());
        return Map.of("registration","Success");
    }
    @PostMapping("/login")
    public Map<String,String> performLogin(@RequestBody AuthDTO authDTO){
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authDTO.getUsername(),authDTO.getPassword());
        try {
            authenticationManager.authenticate(authenticationToken);
        }catch (BadCredentialsException e){
            return Map.of("message","Incorrect credentials");
        }
        User user = customUserDetailsService.findUserByUsername(authDTO.getUsername());
        String token = jwtUtil.generateTokenWithRole(user.getUsername(),user.getRole().getName());
        return Map.of("jwt-token",token);
    }

    @GetMapping("/sigma")
    public Map<String,String> sigma(){
        return Map.of("I simga","SIGMA");
    }

    public User convertToUser(UserDTO userDTO){
        return mapper.map(userDTO,User.class);
    }
}
