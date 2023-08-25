package com.najasin.security.oauth.common.mapper;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.najasin.domain.user.entity.User;
import com.najasin.security.model.PrincipalUser;

@Component
public class PrincipalUserMapper {
    public PrincipalUser toPrincipalUser(User user) {
        Map<String, Object> attributes = new HashMap<>() {{
            put("id", user.getId());
        }};

        return new PrincipalUser(user, attributes, user.getRole());
    }
}
