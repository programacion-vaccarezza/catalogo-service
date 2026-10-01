package com.example.catalogo_service.auth.infrastructure.persistence.adapter;

import com.example.catalogo_service.auth.domain.model.User;
import com.example.catalogo_service.auth.domain.ports.out.UserRepository;
import com.example.catalogo_service.auth.infrastructure.persistence.entity.UserEntity;
import com.example.catalogo_service.auth.infrastructure.persistence.mapper.UserMapper;
import com.example.catalogo_service.auth.infrastructure.persistence.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaUserRepositoryAdapter implements UserRepository {

    private final JpaUserRepository jpaUserRepository;
    private final UserMapper userMapper;

    @Override
    public User save(User user) {
        UserEntity userEntity = userMapper.toEntity(user);
        UserEntity savedEntity = jpaUserRepository.save(userEntity);
        return userMapper.toDomainModel(savedEntity);
    }

    @Override
    public Optional<User> findByLogin(String loginName) {
        return jpaUserRepository.findByLogin(loginName)
                .map(userMapper::toDomainModel);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email)
                .map(userMapper::toDomainModel);
    }

    @Override
    public boolean existsByLogin(String loginName) {
        return jpaUserRepository.existsByLogin(loginName);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }
}
