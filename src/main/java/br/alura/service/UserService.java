package br.alura.service;

import br.alura.config.security.JWTCreator;
import br.alura.config.security.JWTObject;
import br.alura.dto.LoginDTO;
import br.alura.dto.TokenDTO;
import br.alura.entity.User;
import br.alura.enums.Role;
import br.alura.exception.EntityNotFoundException;
import br.alura.exception.PasswordIncorrectException;
import br.alura.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${security.config.prefix}")
    public String prefix;

    @Value("${security.config.key}")
    public String key;

    @Value("${security.config.expiration}")
    public long expiration;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void addUser() {
        final User view = new User()
                .builder()
                .username("view")
                .password(passwordEncoder.encode("admin_v"))
                .roles(List.of(Role.USER_V))
                .build();

        final User edit = new User()
                .builder()
                .username("edit")
                .password(passwordEncoder.encode("admin_e"))
                .roles(List.of(Role.USER_E, Role.USER_V))
                .build();

        userRepository.saveAll(List.of(view, edit));
    }

    public TokenDTO login(final LoginDTO login) {
        log.info("finding user {} ", login.getUsername());
        final Optional<User> user = userRepository.findById(login.getUsername());

        if (user.isEmpty()) {
            log.info("user {} not found! ", login.getUsername());
            throw new EntityNotFoundException(login.getUsername());
        }

        log.info("user {} found! ", login.getUsername());

        final boolean isPasswordCorrect = passwordEncoder.matches(login.getPassword(), user.get().getPassword());
        if (!isPasswordCorrect) {
            throw new PasswordIncorrectException(login.getUsername());
        }

        final JWTObject jwtObject = new JWTObject();
        jwtObject.setIssuedAt(new Date(System.currentTimeMillis()));
        jwtObject.setExpiration((new Date(System.currentTimeMillis() + expiration)));
        jwtObject.setRoles(Role.roleToString(user.get().getRoles()));
        jwtObject.setSubject(JWTCreator.create(jwtObject, "sYLzehcWaYf6JDWVM3UjlmgVsbeCxaj", "Bearer"));

        return new TokenDTO()
                .builder()
                .token(jwtObject.getSubject())
                .expiration(jwtObject.getExpiration())
                .build();
    }
}
