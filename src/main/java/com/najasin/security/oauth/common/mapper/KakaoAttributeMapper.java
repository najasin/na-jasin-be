package com.najasin.security.oauth.common.mapper;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.najasin.domain.user.entity.enums.Provider;
import com.najasin.security.model.OAuth2Request;

@Component
public class KakaoAttributeMapper implements AttributeMapper{

    @Override
    public OAuth2Request mapToDto(Map<String, Object> attributes) {
        String providerId = (attributes.get("sub")).toString();
        String name = (attributes.get("nickname")).toString();
        String email = (attributes.get("email")).toString();

        if(email == null || email.isEmpty()) {
            email = null;
        }

        return new OAuth2Request(Provider.KAKAO, providerId, name, email);
    }
}
