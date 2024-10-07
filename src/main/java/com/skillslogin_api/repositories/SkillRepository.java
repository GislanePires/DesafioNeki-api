package com.skillslogin_api.repositories;

import com.skillslogin_api.domain.user.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, String> {
}
