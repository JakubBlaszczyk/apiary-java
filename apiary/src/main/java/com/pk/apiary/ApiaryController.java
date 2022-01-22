package com.pk.apiary;

import java.util.List;

import com.pk.apiary.request.ApiaryCreate;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@org.springframework.stereotype.Controller
public class ApiaryController {

  private ApiaryService apiaryService;

  @GetMapping("/apiary")
  public String getApiaryPage() {
    return "apiary";
  }

  @PostMapping("/apiary")
  public Integer registerApiary(ApiaryCreate apiaryCreate) {
    return apiaryService.save(apiaryCreate);
  }

  @PutMapping("/apiary")
  public Boolean updateApiary(Apiary apiary) {
    return apiaryService.update(apiary);
  }

  @PutMapping("/apiaries")
  public List<Apiary> getAllApiaries() {
    return apiaryService.getAll();
  }
}
