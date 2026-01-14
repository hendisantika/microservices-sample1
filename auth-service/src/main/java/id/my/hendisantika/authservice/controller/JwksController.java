package id.my.hendisantika.authservice.controller;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Project : microservices-sample1
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 15/01/26
 * Time: 05.20
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/auth/.well-known")
public class JwksController {

    private final JWKSet jwkSet;

    public JwksController(JWKSet jwkSet) {
        this.jwkSet = jwkSet;
    }

    @GetMapping("/jwks.json")
    public Map<String, Object> keys() {
        return new JWKSet(
                jwkSet.getKeys()
                        .stream()
                        .map(JWK::toPublicJWK)
                        .toList()
        ).toJSONObject();
    }
}
