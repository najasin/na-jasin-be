package com.najasin.security.oauth.common.mapper;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.najasin.domain.user.entity.enums.Provider;
import com.najasin.security.model.OAuth2Request;

@Component
public class GoogleAttributeMapper implements AttributeMapper {
    @Override
    public OAuth2Request mapToDto(Map<String, Object> attributes) {
        String providerId = (String) attributes.get("sub");
        String name = (String) attributes.get("name");
        String email = (String) attributes.get("email");

        return new OAuth2Request(Provider.GOOGLE, providerId, name, email);
    }
}
