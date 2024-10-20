package com.brytvich.helpdesk.services;


import com.brytvich.helpdesk.models.Role;
import com.brytvich.helpdesk.models.User;
import com.brytvich.helpdesk.repositories.RoleRepository;
import com.brytvich.helpdesk.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    @Autowired
    public RegistrationService(UsersRepository usersRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public void register(User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow(()->new RuntimeException("Role not found"));
        user.setRole(userRole);
        user.setPassword(encodedPassword);
        usersRepository.save(user);
    }
}
