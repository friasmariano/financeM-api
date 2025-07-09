package com.finance.manager.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class CustomJwtGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        if (resourceAccess == null)
            return Collections.emptyList();

        @SuppressWarnings("unchecked")
        Map<String, Object> userAccess = (Map<String, Object>) resourceAccess.get("user");
        if (userAccess == null)
            return Collections.emptyList();

        @SuppressWarnings("unchecked")
        List<String> roles = (List<String>) userAccess.get("roles");
        if (roles == null) return Collections.emptyList();

        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

    }
}
