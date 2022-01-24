package com.pk.account.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateAccount {

  @NotNull
  @NotBlank
  String login;
  @NotNull
  @NotBlank
  String password;
  @NotNull
  @NotBlank
  @Email
  String email;
  @NotNull
  String privilege;

}
