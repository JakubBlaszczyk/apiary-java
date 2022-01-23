package com.pk.apiary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import com.pk.apiary.request.ApiaryCreate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class TestService {
  private DataSource dataSource;
  private ApiaryService apiaryService;

  @BeforeEach
  public void initialization() {
    this.dataSource = new EmbeddedDatabaseBuilder()
        .setType(EmbeddedDatabaseType.H2)
        .addScripts("schema.sql", "data.sql")
        .build();
    JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
    ApiaryRepository accountRepository = new ApiaryPersistent(jdbcTemplate);
    this.apiaryService = new ApiaryService(accountRepository);
  }

  @AfterEach
  public void cleanup() {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
    jdbcTemplate.execute("DROP ALL OBJECTS");
  }

  @Test
  @Order(0)
  public void testGetAll() {
    List<Apiary> template = Collections.emptyList();
    this.apiaryService.getAll().stream().forEach((e) -> {
      System.out.println(e.getId());
    });
    assertNotEquals(template, this.apiaryService.getAll());
  }

  @Test
  @Order(1)
  public void testFindById() {
    assertNotNull(this.apiaryService.findById(1));
    assertNotNull(this.apiaryService.findById(2));
    assertNull(this.apiaryService.findById(999));
  }

  @Test
  @Order(2)
  public void testSave() {
    assertEquals(this.apiaryService.getAll().size() + 1, this.apiaryService
        .save(new ApiaryCreate("test", "test")));
  }

  @Test
  @Order(3)
  public void testUpdate() {
    
    Apiary checker = this.apiaryService.findById(3);
    assertTrue(this.apiaryService.update(new Apiary(3, "updated", null)));
    assertEquals("updated", this.apiaryService.findById(3).getLocalization());
    assertEquals(checker.getInformation(), this.apiaryService.findById(3).getInformation());
  }

  @Test
  @Order(4)
  public void testDeleteById() {
    // need to delete id without any foreign keys restrictions
    assertTrue(this.apiaryService.deleteById(4));
    assertFalse(this.apiaryService.deleteById(999));
  }
}
