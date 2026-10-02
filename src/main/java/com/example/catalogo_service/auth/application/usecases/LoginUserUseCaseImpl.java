package com.example.catalogo_service.auth.application.usecases;

import com.example.catalogo_service.auth.application.exception.InvalidCredentialsException;
import com.example.catalogo_service.auth.domain.model.User;
import com.example.catalogo_service.auth.domain.ports.in.LoginUserUseCase;
import com.example.catalogo_service.auth.domain.ports.out.PasswordHasher;
import com.example.catalogo_service.auth.domain.ports.out.TokenProvider;
import com.example.catalogo_service.auth.domain.ports.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginUserUseCaseImpl implements LoginUserUseCase {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final TokenProvider tokenProvider;

    @Override
    public String loginUser(String loginName, String password) {
        User user = userRepository.findByLogin(loginName)
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordHasher.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return tokenProvider.generateToken(user);
    }
}
