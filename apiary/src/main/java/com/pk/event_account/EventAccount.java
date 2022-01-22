package com.pk.event_account;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class EventAccount {
  @NotNull
  Integer idEvent;
  @NotNull
  Integer idAccount;
}
