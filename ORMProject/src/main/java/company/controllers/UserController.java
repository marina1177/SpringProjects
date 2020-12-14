package company.controllers;

import company.CompanyEntity;
import company.repository.CompanyRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private CompanyRepository companyRepository;

  @GetMapping(value = "/all")
  public List<CompanyEntity> getAll(){
    return companyRepository.findAll();
  }

  @PostMapping(value = "/load")
  public CompanyEntity load(@RequestBody final CompanyEntity companyEntity) {
    return companyRepository.save(companyEntity);
  }

}
