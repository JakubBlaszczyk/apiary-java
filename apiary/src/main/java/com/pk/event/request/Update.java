package com.pk.event.request;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Update {
  @NotNull
  Integer id;
  Integer idApiary;
  Timestamp start;
  Timestamp end;
  String note;
}
