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

import com.pk.apiary.request.Create;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class Database {
  private DataSource dataSource;
  private Repository apiaryRepository;

  @BeforeEach
  public void initialization() {
    this.dataSource = new EmbeddedDatabaseBuilder()
        .setType(EmbeddedDatabaseType.H2)
        .addScripts("schema.sql", "data.sql")
        .build();
    JdbcTemplate jdbcTemplate = new JdbcTemplate(this.dataSource);
    this.apiaryRepository = new Persistent(jdbcTemplate);
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
    this.apiaryRepository.getAll().stream().forEach((e) -> {
      System.out.println(e.getId());
    });
    assertNotEquals(template, this.apiaryRepository.getAll());
  }

  @Test
  @Order(1)
  public void testFindById() {
    assertNotNull(this.apiaryRepository.findById(1));
    assertNotNull(this.apiaryRepository.findById(2));
    assertNull(this.apiaryRepository.findById(999));
  }

  @Test
  @Order(2)
  public void testSave() {
    assertEquals(this.apiaryRepository.getAll().size() + 1, this.apiaryRepository
        .save(new Create("test", "test")));
  }

  @Test
  @Order(3)
  public void testUpdate() {
    assertTrue(this.apiaryRepository.update(new Apiary(2, "updated", "updated")));
    assertEquals("updated", this.apiaryRepository.findById(3).getInformation());
  }

  @Test
  @Order(4)
  public void testDeleteById() {
    // need to delete id without any foreign keys
    assertTrue(this.apiaryRepository.deleteById(4));
    assertFalse(this.apiaryRepository.deleteById(999));
  }
}
