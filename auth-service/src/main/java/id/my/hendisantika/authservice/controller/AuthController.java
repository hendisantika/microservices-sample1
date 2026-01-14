package id.my.hendisantika.authservice.controller;

import id.my.hendisantika.authservice.domain.LoginRequest;
import id.my.hendisantika.authservice.domain.UserRole;
import id.my.hendisantika.authservice.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Project : microservices-sample1
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 15/01/26
 * Time: 05.18
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {
        if ("admin".equals(request.username())
                && "admin123".equals(request.password())) {

            return Map.of(
                    "access_token",
                    authService.generateToken(
                            request.username(),
                            UserRole.ADMIN
                    )
            );
        }
        if ("user".equals(request.username())
                && "user123".equals(request.password())) {

            return Map.of(
                    "access_token",
                    authService.generateToken(
                            request.username(),
                            UserRole.USER
                    )
            );
        }

        throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "Invalid credentials"
        );
    }
}
