package com.pk.account.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.pk.account.Privilege;

import lombok.Value;

@Value
public class Create {

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
  Privilege privilege;

}
