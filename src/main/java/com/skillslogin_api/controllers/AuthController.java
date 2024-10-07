package com.skillslogin_api.controllers;

import com.skillslogin_api.domain.user.Skill;
import com.skillslogin_api.domain.user.User;
import com.skillslogin_api.domain.user.UserSkill;
import com.skillslogin_api.dto.LoginRequestDTO;
import com.skillslogin_api.dto.RegisterRequestDTO;
import com.skillslogin_api.dto.ResponseDTO;
import com.skillslogin_api.dto.UserSkillDTO;
import com.skillslogin_api.infra.security.TokenService;
import com.skillslogin_api.repositories.SkillRepository;
import com.skillslogin_api.repositories.UserRepository;
import com.skillslogin_api.repositories.UserSkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final SkillRepository skillRepository;
    private final UserSkillRepository userSkillRepository;



    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body){
        User user = this.repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body){
        Optional<User> user = this.repository.findByEmail(body.email());

        if(user.isEmpty()) {
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setEmail(body.email());
            newUser.setName(body.name());
            this.repository.save(newUser);

            String token = this.tokenService.generateToken(newUser);
            return ResponseEntity.ok(new ResponseDTO(newUser.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/skills")
    public ResponseEntity addSkill(@RequestBody Skill skill) {
        Skill newSkill = skillRepository.save(skill);
        return ResponseEntity.ok(newSkill);
    }

    @PostMapping("user-skills")
    public ResponseEntity associateSkill(@RequestBody UserSkillDTO userSkillDTO) {
        User user = this.repository.findById(userSkillDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        Skill skill = skillRepository.findById(userSkillDTO.getSkillId()).orElseThrow(() -> new RuntimeException("Skill not found"));

        UserSkill userSkill = new UserSkill();
        userSkill.setUser(user);
        userSkill.setSkill(skill);
        userSkill.setLevel(userSkillDTO.getLevel());

        userSkillRepository.save(userSkill);
        return ResponseEntity.ok("Skill adicionada com sucesso!");
    }


}