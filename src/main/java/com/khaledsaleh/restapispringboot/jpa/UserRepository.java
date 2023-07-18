package com.khaledsaleh.restapispringboot.jpa;

import com.khaledsaleh.restapispringboot.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
