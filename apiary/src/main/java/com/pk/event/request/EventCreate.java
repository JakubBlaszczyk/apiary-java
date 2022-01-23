package com.pk.event.request;

import java.sql.Timestamp;
import javax.validation.constraints.NotNull;
import lombok.Value;

@Value
public class EventCreate {

  @NotNull
  Integer idApiary;
  Timestamp start;
  Timestamp end;
  String note;
}
