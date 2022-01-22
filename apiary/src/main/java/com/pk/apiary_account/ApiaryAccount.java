package com.pk.apiary_account;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class ApiaryAccount {
  @NotNull
  Integer idApiary;
  @NotNull
  Integer idAccount;
}
