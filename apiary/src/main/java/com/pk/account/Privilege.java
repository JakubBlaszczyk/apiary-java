package com.pk.account;

public enum Privilege {
  WORKER,
  BEEKEEPER,
  ADMIN;

  public static Privilege stringToPrivilege(String string) {
    switch (string.toLowerCase()) {
      case "worker":
      case "user":
        return WORKER;
      case "beekeeper":
        return BEEKEEPER;
      case "admin":
        return ADMIN;
      // not good solution, but for the meantime (meaning forever)
      default:
        return WORKER;
    }
  }
}
