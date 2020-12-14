package com.orm.repository;

import com.orm.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<Users,Long> {

}
