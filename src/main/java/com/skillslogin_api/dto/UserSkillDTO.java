package com.skillslogin_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSkillDTO {
    private String userId;
    private String skillId;
    private int level;
}
