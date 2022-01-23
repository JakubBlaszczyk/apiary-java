package com.pk.account.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateAccount {
  @NotNull
  Integer id;
  String login;
  String password;
  @Email
  String email;
}
