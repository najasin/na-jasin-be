package com.najasin.security.oauth.common.mapper;

import java.util.Map;

import com.najasin.security.model.OAuth2Request;

public interface AttributeMapper {
    OAuth2Request mapToDto(Map<String, Object> attributes);
}
