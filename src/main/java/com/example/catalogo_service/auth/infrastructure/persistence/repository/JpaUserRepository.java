package com.example.catalogo_service.auth.infrastructure.persistence.repository;

import com.example.catalogo_service.auth.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Repositorio de la entidad UserEntity
@Repository
public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {

    // Busca por login
    Optional<UserEntity> findByLogin(String login);
    // Busca por email
    Optional<UserEntity> findByEmail(String email);
    // Indica si existe un usuario con ese login
    boolean existsByLogin(String login);
    // Indica si existe un usuario con ese email
    boolean existsByEmail(String email);
}
