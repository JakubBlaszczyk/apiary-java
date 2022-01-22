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

public class TestPersistent {
  private DataSource dataSource;
  private Repository apiaryAccountRepository;

  @BeforeEach
  public void initialization() {
    this.dataSource = new EmbeddedDatabaseBuilder()
        .setType(EmbeddedDatabaseType.H2)
        .addScripts("schema.sql", "data.sql")
        .build();
    JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
    this.apiaryAccountRepository = new Persistent(jdbcTemplate);
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
    this.apiaryAccountRepository.getAll().stream().forEach((e) -> {
      System.out.println(e.getIdAccount() + " " + e.getIdApiary());
    });
    assertNotEquals(template, this.apiaryAccountRepository.getAll());
  }

  @Test
  @Order(1)
  public void testFindById() {
    List<ApiaryAccount> template = Collections.emptyList();
    assertNotEquals(template, this.apiaryAccountRepository.findByIdApiary(1));
    assertNotEquals(template, this.apiaryAccountRepository.findByIdAccount(2));
    assertNotNull(this.apiaryAccountRepository.findByIds(2, 1));
    assertEquals(template, this.apiaryAccountRepository.findByIdApiary(999));
    assertEquals(template, this.apiaryAccountRepository.findByIdAccount(999));
    assertNull(this.apiaryAccountRepository.findByIds(999, 999));
  }

  @Test
  @Order(2)
  public void testSave() {
    assertTrue(this.apiaryAccountRepository
        .save(new ApiaryAccount(3, 1)));
  }

  @Test
  @Order(3)
  public void testDeleteById() {
    assertTrue(this.apiaryAccountRepository.deleteById(2, 1));
    assertFalse(this.apiaryAccountRepository.deleteById(999, 999));
  }
}
