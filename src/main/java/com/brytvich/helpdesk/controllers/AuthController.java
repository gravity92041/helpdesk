package com.brytvich.helpdesk.controllers;

import com.brytvich.helpdesk.dto.AuthDTO;
import com.brytvich.helpdesk.dto.PersonDTO;
import com.brytvich.helpdesk.models.Person;
import com.brytvich.helpdesk.security.JWTUtil;
import com.brytvich.helpdesk.security.PersonDetails;
import com.brytvich.helpdesk.services.PersonDetailsService;
import com.brytvich.helpdesk.services.RegistrationService;
import jakarta.validation.Valid;
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

    private final PersonDetailsService personDetailsService;

    private final AuthenticationManager authenticationManager;

    private final JWTUtil jwtUtil;

    private final ModelMapper mapper;
    @Autowired
    public AuthController(RegistrationService registrationService, AuthenticationManager authenticationManager, JWTUtil jwtUtil, ModelMapper mapper, PersonDetailsService personDetailsService) {
        this.registrationService = registrationService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.mapper = mapper;
        this.personDetailsService = personDetailsService;
    }

//    @GetMapping("/login")
//    public String login

    //ПЕРЕПИСАТЬ МЕТОДЫ ПОД HTTPENTITY
    @PostMapping("/registration")
    public Map<String,String> performRegistration (@RequestBody @Valid PersonDTO personDTO,
                                                   BindingResult bindingResult){
        Person person = convertToPerson(personDTO);

        if (bindingResult.hasErrors()){
            return Map.of("message","error");
        }
        registrationService.register(person);
        String token  = jwtUtil.generateToken(person.getUsername());
        return Map.of("jwt-token",token);
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
        Person person = personDetailsService.findPersonByUsername(authDTO.getUsername());
        String token = jwtUtil.generateTokenWithRole(person.getUsername(),person.getRole());
        return Map.of("jwt-token",token);
    }

    public Person convertToPerson(PersonDTO personDTO){
        return mapper.map(personDTO,Person.class);
    }
}
