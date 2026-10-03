package com.example.catalogo_service.auth.domain.ports.out;

import com.example.catalogo_service.auth.domain.model.User;

import java.util.Optional;

public interface UserRepository {

    User save(User user);
    Optional<User> findByLogin(String loginName);
    Optional<User> findByEmail(String email);
    boolean existsByLogin(String loginName);
    boolean existsByEmail(String email);

}
