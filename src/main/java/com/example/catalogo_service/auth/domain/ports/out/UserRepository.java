package com.example.catalogo_service.auth.domain.ports.out;

import com.example.catalogo_service.auth.domain.model.User;

import java.util.Optional;

// Port de salida del repositorio de usuarios
public interface UserRepository {

    // Crea o actualiza un usuario
    User save(User user);
    // Busca un usuario por login
    Optional<User> findByLogin(String loginName);
    // Busca un usuario por login
    Optional<User> findByEmail(String email);
    // Busca un usuario por email
    boolean existsByLogin(String loginName);
    // Indica si ya existe un usuario con ese login
    boolean existsByEmail(String email);
    // Indica si ya existe un usuario con ese email

}
