package com.example.catalogo_service.auth.application.usecases;

import com.example.catalogo_service.auth.application.exception.UserNotFoundException;
import com.example.catalogo_service.auth.domain.model.User;
import com.example.catalogo_service.auth.domain.ports.in.GetUserByLoginUseCase;
import com.example.catalogo_service.auth.domain.ports.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetUserByLoginUseCaseImpl implements GetUserByLoginUseCase {

    private final UserRepository userRepository;

    @Override
    public User getUserByLogin(String loginName) {
        return userRepository.findByLogin(loginName)
                .orElseThrow(() -> new UserNotFoundException(loginName));
    }
}
