package com.skillslogin_api.controllers;

import com.skillslogin_api.domain.user.User;
import com.skillslogin_api.domain.user.UserSkill;
import com.skillslogin_api.dto.UserWithSkillsDTO;
import com.skillslogin_api.repositories.UserRepository;
import com.skillslogin_api.repositories.UserSkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserSkillRepository userSkillRepository;

    @GetMapping
    public ResponseEntity<String> getUser(){
        return ResponseEntity.ok("Autorizado!");
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users-skills")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<List<UserWithSkillsDTO>> listUserWithSkills() {
        List<User> users = userRepository.findAll();
        List<UserSkill> userSkills = userSkillRepository.findAll();

        List<UserWithSkillsDTO> response = users.stream().map(user -> {
            List<UserWithSkillsDTO.SkillWithLevelDTO> skills = userSkills.stream()
                    .filter(userSkill -> userSkill.getUser().getId().equals(user.getId()))
                    .map(userSkill -> {
                        System.out.println("Skill ID: " + userSkill.getSkill().getId());
                        System.out.println("Skill Name: " + userSkill.getSkill().getName());
                        return new UserWithSkillsDTO.SkillWithLevelDTO(
                                userSkill.getSkill().getId(),
                                userSkill.getSkill().getName(),
                                userSkill.getLevel());
                    })
                    .collect(Collectors.toList());
            return new UserWithSkillsDTO(user.getId(), user.getName(), skills);}).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
