package com.orm.controllers;

import com.orm.repository.UserJpaRepository;
import com.orm.entity.Users;
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
  private UserJpaRepository userJpaRepository;

  @GetMapping(value = "/all")
  public void getAll(){
    Users users = new Users();
    users.setName("Marina");
    users.setSalary(2500000);
     userJpaRepository.save(users);
  }

  @PostMapping(value = "/load")
  public Users load(@RequestBody final Users users) {
    return userJpaRepository.save(users);
  }

}
