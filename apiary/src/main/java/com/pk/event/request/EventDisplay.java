package com.pk.event.request;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventDisplay {
  @NotNull
  Integer id;
  @NotNull
  Integer idApiary;
  String start;
  String end;
  String note;
}
