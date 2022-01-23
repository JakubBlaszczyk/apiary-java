package com.pk.apiary;

import java.util.List;

import com.pk.apiary.request.ApiaryCreate;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class ApiaryController {

  private ApiaryService apiaryService;

  @PreAuthorize("hasAnyAuthority('WORKER', 'BEEKEEPER', 'ADMIN')")
  @GetMapping("/apiary")
  public String getApiaryPage() {
    return "apiary";
  }

  @PostMapping("/apiary")
  public void registerApiary(ApiaryCreate apiaryCreate) {
    apiaryService.save(apiaryCreate);
  }

  @PutMapping("/apiary")
  public Boolean updateApiary(Apiary apiary) {
    return apiaryService.update(apiary);
  }

  @GetMapping("/apiaries")
  public String getApiaryHtml(Model model) {
    model.addAttribute("apiaries", this.apiaryService.getAll());
    return "Apiary";
  }

  @GetMapping("/apiaryAdd")
  public String getAddApiary() {
    return "AddApiary";
  }

  @GetMapping("/apiaryEdit")
  public String getEditApiary() {
    return "EditApiary";
  }

  @PutMapping("/apiaries")
  public @ResponseBody List<Apiary> getAllApiaries() {
    return apiaryService.getAll();
  }
}
