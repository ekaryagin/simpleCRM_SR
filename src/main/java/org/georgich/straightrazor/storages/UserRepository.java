package org.georgich.straightrazor.storages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.georgich.straightrazor.domain.User;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);

    User findByEmail(String email);
}