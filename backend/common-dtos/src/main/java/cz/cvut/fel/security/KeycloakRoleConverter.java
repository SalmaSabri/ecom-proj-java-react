package cz.cvut.fel.security;


import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A converter that extracts roles from a JWT token provided by Keycloak and converts them
 * into a collection of {@link GrantedAuthority} objects. The roles are prefixed with "ROLE_"
 * to match Spring Security conventions.
 */
public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    /**
     * Converts the roles from the JWT's "realm_access" claim into a collection of
     * {@link GrantedAuthority} objects.
     *
     * @param source the {@link Jwt} token containing the claims.
     * @return a collection of granted authorities representing the roles, or an empty list if no roles are found.
     */
    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        Map<String, Object> realmAccess = (Map<String, Object>) source.getClaims().get("realm_access");

        if (realmAccess == null || realmAccess.isEmpty())  {
            return new ArrayList<>();
        }

        Collection<GrantedAuthority> returnValue = ((List<String>) realmAccess.get("roles"))
                .stream().map(roleName -> "ROLE_" + roleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return returnValue;
    }
}
