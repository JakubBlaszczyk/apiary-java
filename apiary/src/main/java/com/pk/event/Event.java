package com.pk.event;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Event {
  @NotNull
  Integer id;
  @NotNull
  Integer idApiary;
  Timestamp start;
  Timestamp end;
  String note;
}
