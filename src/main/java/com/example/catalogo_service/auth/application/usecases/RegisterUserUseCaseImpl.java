package com.example.catalogo_service.auth.application.usecases;

import com.example.catalogo_service.auth.application.exception.UserAlreadyExistsException;
import com.example.catalogo_service.auth.domain.model.User;
import com.example.catalogo_service.auth.domain.ports.in.RegisterUserUseCase;
import com.example.catalogo_service.auth.domain.ports.out.PasswordHasher;
import com.example.catalogo_service.auth.domain.ports.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterUserUseCaseImpl implements RegisterUserUseCase {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    @Override
    public User registerUser(User user) {
        if (userRepository.existsByLogin(user.getLogin())) {
            throw new UserAlreadyExistsException(user.getLogin());
        }
        if (user.getEmail() != null && userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException(user.getEmail());
        }

        if (user.getLangKey() == null) {
            user.setLangKey("es");
        }
        user.setPassword(passwordHasher.hash(user.getPassword()));
        user.setActivated(true);

        return userRepository.save(user);
    }
}
