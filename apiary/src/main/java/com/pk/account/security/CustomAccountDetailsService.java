package com.pk.account.security;

import com.pk.account.Account;
import com.pk.account.AccountService;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class CustomAccountDetailsService implements UserDetailsService {

  private AccountService accountService;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    try {
      Account account = accountService.findByLogin(username);
      log.info(username);
      return CustomAccountDetails.build(account);
    } catch (Exception exception) {
      log.error("Exception", exception);
      throw new UsernameNotFoundException("Didn't find an account");
    }
  }
}
