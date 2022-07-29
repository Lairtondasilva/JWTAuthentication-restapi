package com.exportech.encriptografarusuario.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exportech.encriptografarusuario.model.UserModel;
import com.exportech.encriptografarusuario.repository.UserRepository;
import com.exportech.encriptografarusuario.service.UserService;

@RestController
@RequestMapping("api/usuario")
public class UserController {
  
  private final UserRepository userRepository;
  private final UserService userService;

  public UserController(UserRepository userRepository, UserService userService){
    this.userRepository = userRepository;
    this.userService = userService;
  }

  @GetMapping("/listarTodos")
  public ResponseEntity<List<UserModel>> getAll (){
    return ResponseEntity.ok(userRepository.findAll());
  }

  @PostMapping("/cadastrar")
  public ResponseEntity<UserModel> register (@RequestBody UserModel userModel){
    return ResponseEntity.of(userService.registerUser(userModel));
  }

  @PostMapping("/validar")
  public ResponseEntity<Boolean> validar(@RequestParam String login,
  @RequestParam String password){
    return (userService.passwordValidator(login, password)) ?
     ResponseEntity.ok(true) : 
     ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
  }
}
