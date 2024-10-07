package com.skillslogin_api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserWithSkillsDTO {
    private Long userId;
    private String userName;
    private List<SkillWithLevelDTO> skills;

@Getter
@Setter
public static class SkillWithLevelDTO {
    private Long skillId;
    private String skillName;
    private int level;

    public SkillWithLevelDTO(Long skillId, String skillName, int level) {
        this.skillId = skillId;
        this.skillName = skillName;
        this.level = level;
        }
    }
    public UserWithSkillsDTO(Long userId, String userName, List<SkillWithLevelDTO> skills) {
        this.userId = userId;
        this.userName = userName;
        this.skills = skills;
    }
}
