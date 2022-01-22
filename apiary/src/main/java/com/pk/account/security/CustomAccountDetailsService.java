package com.pk.account.security;

import com.pk.account.Account;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomAccountDetailsService implements UserDetailsService {

  private com.pk.account.AccountService accountService;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    try {
      Account account = accountService.findByLogin(username);
      return CustomAccountDetails.build(account);
    } catch (Exception exception) {
      throw new UsernameNotFoundException("Didn't find an account");
    }
  }
}
