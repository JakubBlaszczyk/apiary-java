package com.pk.apiary;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Apiary {
  @NotNull
  Integer id;
  String localization;
  String information;
}
