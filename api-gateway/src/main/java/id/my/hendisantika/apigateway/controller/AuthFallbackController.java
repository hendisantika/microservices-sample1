package id.my.hendisantika.apigateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Project : microservices-sample1
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 15/01/26
 * Time: 05.09
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("fallback/auth")
public class AuthFallbackController {
    @PostMapping
    public Mono<ResponseEntity<Map<String, String>>> authFallBack() {
        return Mono.just(
                ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(Map.of(
                                "error", "AUTH_SERVICE_UNAVAILABLE",
                                "message", "Authentication service is temporarily unavailable. Please try again later."
                        ))
        );
    }

    @GetMapping
    public Mono<ResponseEntity<Map<String, String>>> jwksFallback() {
        return Mono.just(
                ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(Map.of(
                                "error", "JWKS_UNAVAILABLE",
                                "message", "JWKS endpoint is temporarily unavailable."
                        ))
        );
    }
}
