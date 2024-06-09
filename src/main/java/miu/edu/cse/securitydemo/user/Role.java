package miu.edu.cse.securitydemo.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum Role {
    ADMIN(
            Set.of(
                    Permission.ADMIN_WRITE,
                    Permission.ADMIN_READ
            )
    ),
    MEMBER(
            Set.of(
                    Permission.MEMBER_READ,
                    Permission.MEMBER_WRITE
            )
    );

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getSimpleGrantedAuthority() {
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        for (Permission permission : permissions) {
//            authorities.add(new SimpleGrantedAuthority(permission.getPermission()));
//        }
//        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
//        return authorities;
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
