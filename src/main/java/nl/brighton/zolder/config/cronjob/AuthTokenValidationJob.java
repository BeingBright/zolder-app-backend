package nl.brighton.zolder.config.cronjob;

import lombok.RequiredArgsConstructor;
import nl.brighton.zolder.model.user.AuthToken;
import nl.brighton.zolder.service.auth.AuthService;
import nl.brighton.zolder.service.auth.exception.InvalidTokenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@RequiredArgsConstructor
@Configuration
@EnableScheduling
public class AuthTokenValidationJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthTokenValidationJob.class);


    private final AuthService authService;


    @Scheduled(fixedRate = 1000 * 600)
    public void validateKnownTokens() {
        LOGGER.info("Starting token validation job...");
        for (AuthToken token :
                authService.getTokens()) {
            try {
                authService.isValid(token.getToken());
            } catch (InvalidTokenException e) {
                LOGGER.info("Removed Invalid token'{}'",
                        token.getToken());
            }
        }
    }

}
