package nl.brighton.zolder.service;

import nl.brighton.zolder.persistance.entity.TokenEntity;
import nl.brighton.zolder.service.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private TokenEntity tokenEntity;

    @Override
    public boolean isValid(String token) {

        if (tokenEntity.contains(token)) {
            return true;
        }
        throw new InvalidTokenException();
    }

    @Autowired
    public void setTokenEntity(TokenEntity tokenEntity) {
        this.tokenEntity = tokenEntity;
    }
}
