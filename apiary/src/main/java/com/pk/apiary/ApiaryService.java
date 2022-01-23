package com.pk.apiary;

import java.util.List;

import com.pk.apiary.request.ApiaryCreate;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@Service
public class ApiaryService {
  private ApiaryRepository apiaryRepository;

  public List<Apiary> getAll() {
    return apiaryRepository.getAll();
  }

  public Apiary findById(Integer id) {
    return apiaryRepository.findById(id);
  }

  public Boolean deleteById(Integer id) {
    return apiaryRepository.deleteById(id);
  }

  public Boolean update(Apiary apiary) {
    Apiary temp = apiaryRepository.findById(apiary.getId());
    if (temp == null) {
      log.warn("Account doesn't exist");
      return false;
    }

    if (apiary.getInformation() == null || apiary.getInformation().isBlank()) {
      apiary.setInformation(temp.getInformation());
    }

    if (apiary.getLocalization() == null || apiary.getLocalization().isBlank()) {
      apiary.setLocalization(temp.getLocalization());
    }

    return apiaryRepository.update(apiary);
  }

  public Integer save(ApiaryCreate account) {
    return apiaryRepository.save(account);
  }
}
