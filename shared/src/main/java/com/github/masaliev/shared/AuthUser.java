package com.github.masaliev.shared;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public final class AuthUser implements UserDetails {
    
    private Long id;

    private String username;

    private String password;

    private boolean active;

    private Set<UserRole> roles;

    public AuthUser(Long id, String username, String password, boolean active, Set<UserRole> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.active = active;
        this.setRoles(roles);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = new HashSet<UserRole>(roles.size());
        for (UserRole role : roles){
            this.roles.add(role);
        }
    }
    

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public boolean isAccountNonExpired() {
        return active;
    }

    public boolean isAccountNonLocked() {
        return active;
    }

    public boolean isCredentialsNonExpired() {
        return active;
    }

    public boolean isEnabled() {
        return active;
    }

}
