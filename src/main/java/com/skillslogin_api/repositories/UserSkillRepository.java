package com.skillslogin_api.repositories;

import com.skillslogin_api.domain.user.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSkillRepository extends JpaRepository<UserSkill, String> {
}
