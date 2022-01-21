package com.pk.account;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Account {
  @NotNull
  Integer id;
  @NotNull
  String login;
  @NotNull
  String password;
  @NotNull
  @Email
  String email;
  @NotNull
  Privilege privilege;
}
