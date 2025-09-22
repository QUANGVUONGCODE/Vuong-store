package com.example.vuongstore.configuration;

import com.example.vuongstore.entity.Role;
import com.example.vuongstore.entity.User;
import com.example.vuongstore.enums.RolePlay;
import com.example.vuongstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@Configuration
public class ApplicationInitConfig {
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public ApplicationInitConfig(UserRepository userRepository){
        this.userRepository = userRepository;
    }

   @Bean
   ApplicationRunner applicationRunner(){
       return args -> {
           if(userRepository.findByPhoneNumber("admin").isEmpty()){
               Role role = new Role();
               role.setId(2L);
               role.setName(RolePlay.ADMIN.name());
               User user = User.builder()
                       .phoneNumber("admin")
                       .password(passwordEncoder.encode("admin"))
                       .fullName("admin")
                       .address("sss")
                       .dateOfBirth(new Date())
                       .email("admin@gmail.com")
                       .retypePassword("admin")
                       .role(role)
                       .active(true)
                       .build();
               userRepository.save(user);
           }
       };
   }
}
