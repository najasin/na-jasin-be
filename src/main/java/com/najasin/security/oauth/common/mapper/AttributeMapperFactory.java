package com.najasin.security.oauth.common.mapper;

import java.util.EnumMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.najasin.domain.user.entity.enums.Provider;

@Component
public class AttributeMapperFactory {
	private final Map<Provider, AttributeMapper> mapperMap;
	private final GoogleAttributeMapper googleAttributeMapper;
	private final KakaoAttributeMapper kakaoAttributeMapper;

	public AttributeMapperFactory(GoogleAttributeMapper googleAttributeMapper,
		KakaoAttributeMapper kakaoAttributeMapper) {
		this.mapperMap = new EnumMap<>(Provider.class);
		this.googleAttributeMapper = googleAttributeMapper;
		this.kakaoAttributeMapper = kakaoAttributeMapper;

		initialize();
	}

	private void initialize() {
		mapperMap.put(Provider.GOOGLE, googleAttributeMapper);
		mapperMap.put(Provider.KAKAO, kakaoAttributeMapper);
	}

	public AttributeMapper getAttributeMapper(Provider socialProvider) {
		return mapperMap.get(socialProvider);
	}
}
