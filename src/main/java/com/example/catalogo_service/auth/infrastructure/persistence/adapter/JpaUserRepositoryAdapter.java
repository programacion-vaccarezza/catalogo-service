package com.example.catalogo_service.auth.infrastructure.persistence.adapter;

import com.example.catalogo_service.auth.domain.model.User;
import com.example.catalogo_service.auth.domain.ports.out.UserRepository;
import com.example.catalogo_service.auth.infrastructure.persistence.entity.UserEntity;
import com.example.catalogo_service.auth.infrastructure.persistence.mapper.UserMapper;
import com.example.catalogo_service.auth.infrastructure.persistence.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

// Adapter que implementa UserRepository usando JPA, traduciendo entidad y dominio
@Component
@RequiredArgsConstructor
public class JpaUserRepositoryAdapter implements UserRepository {

    private final JpaUserRepository jpaUserRepository;
    private final UserMapper userMapper;

    // Guarda el usuario y devuelve el modelo de dominio ya persistido
    @Override
    public User save(User user) {
        UserEntity userEntity = userMapper.toEntity(user);
        UserEntity savedEntity = jpaUserRepository.save(userEntity);
        return userMapper.toDomainModel(savedEntity);
    }

    // Busca por login y mapea a dominio si existe
    @Override
    public Optional<User> findByLogin(String loginName) {
        return jpaUserRepository.findByLogin(loginName)
                .map(userMapper::toDomainModel);
    }

    // Busca por email y mapea a dominio si existe
    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email)
                .map(userMapper::toDomainModel);
    }

    // Delega la verificación de existencia por login en el repositorio JPA
    @Override
    public boolean existsByLogin(String loginName) {
        return jpaUserRepository.existsByLogin(loginName);
    }

    // Delega la verificación de existencia por email en el repositorio JPA
    @Override
    public boolean existsByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }
}
