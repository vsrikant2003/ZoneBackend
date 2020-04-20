package com.zone.zissa.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.stereotype.Component;

import com.zone.zissa.model.Role;
import com.zone.zissa.model.User;
import com.zone.zissa.repos.UserRepository;

/**
 * The UserDetailService Class.
 */
@Component
public class UserDetailService implements UserDetailsContextMapper {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails mapUserFromContext(DirContextOperations ctx, String username,
            Collection<? extends GrantedAuthority> authorities) {
        User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username not found in DB");
        }
        Role role = user.getRole();
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getEmail(), true, true,
                true, true, getGrantedAuthorities(role));
    }

    private List<GrantedAuthority> getGrantedAuthorities(Role roleobj) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        String role;

        if (roleobj.getAdministration() == 1) {
            role = "ADMIN";
        } else {
            role = "USER";
        }
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return authorities;
    }

    @Override
    public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {
        throw new UnsupportedOperationException();
    }
}
