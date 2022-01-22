package com.pk.apiary_account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Collections;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class TestServices {
  private DataSource dataSource;
  private Service apiaryAccountService;

  @BeforeEach
  public void initialization() {
    this.dataSource = new EmbeddedDatabaseBuilder()
        .setType(EmbeddedDatabaseType.H2)
        .addScripts("schema.sql", "data.sql")
        .build();
    JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
    Repository eventAccountRepository = new Persistent(jdbcTemplate);
    this.apiaryAccountService = new Service(eventAccountRepository,
        new com.pk.apiary.Service(new com.pk.apiary.Persistent(jdbcTemplate)),
        new com.pk.account.Service(new com.pk.account.Persistent(jdbcTemplate)));
  }

  @AfterEach
  public void cleanup() {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
    jdbcTemplate.execute("DROP ALL OBJECTS");
  }

  @Test
  @Order(0)
  public void testGetAll() {
    List<ApiaryAccount> template = Collections.emptyList();
    this.apiaryAccountService.getAll().stream().forEach((e) -> {
      System.out.println(e.getIdAccount() + " " + e.getIdApiary());
    });
    assertNotEquals(template, this.apiaryAccountService.getAll());
  }

  @Test
  @Order(1)
  public void testFindById() {
    List<ApiaryAccount> template = Collections.emptyList();
    assertNotEquals(template, this.apiaryAccountService.findByIdApiary(1));
    assertNotEquals(template, this.apiaryAccountService.findByIdAccount(2));
    assertNotNull(this.apiaryAccountService.findByIds(2, 1));
    assertEquals(template, this.apiaryAccountService.findByIdApiary(999));
    assertEquals(template, this.apiaryAccountService.findByIdAccount(999));
    assertNull(this.apiaryAccountService.findByIds(999, 999));
  }

  @Test
  @Order(2)
  public void testSave() {
    assertTrue(this.apiaryAccountService
        .save(new ApiaryAccount(2, 3)));
  }

  @Test
  @Order(3)
  public void testDeleteById() {
    assertTrue(this.apiaryAccountService.deleteById(2, 1));
    assertFalse(this.apiaryAccountService.deleteById(999, 999));
  }
}
