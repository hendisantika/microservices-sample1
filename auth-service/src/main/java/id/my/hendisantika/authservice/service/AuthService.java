package id.my.hendisantika.authservice.service;

import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * Project : microservices-sample1
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 15/01/26
 * Time: 05.17
 * To change this template use File | Settings | File Templates.
 */
@Service
public class AuthService {

    private final JwtEncoder jwtEncoder;

    public AuthService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }
}
