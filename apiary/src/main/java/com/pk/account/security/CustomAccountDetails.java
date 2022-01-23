package com.pk.account.security;

import java.util.Collection;
import java.util.List;

import com.pk.account.Account;
import com.pk.account.Privilege;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomAccountDetails implements UserDetails {

  Integer id;
  String username;
  String password;
  String email;

  GrantedAuthority authority;

  public static CustomAccountDetails build(Account account) {
    return new CustomAccountDetails(
        account.getId(),
        account.getLogin(),
        account.getPassword(),
        account.getEmail(),
        new SimpleGrantedAuthority(Privilege.privilegeToString(account.getPrivilege())));
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(this.authority);
  }

  public GrantedAuthority getAuthority() {
    return this.authority;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}
