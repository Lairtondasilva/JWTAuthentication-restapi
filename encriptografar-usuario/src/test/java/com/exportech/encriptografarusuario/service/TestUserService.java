package com.exportech.encriptografarusuario.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import com.exportech.encriptografarusuario.model.UserModel;
import com.exportech.encriptografarusuario.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class TestUserService {
  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private UserService userService;

  private String nome = "Lairton";
  private String login = "lairton@gmail.com";
  private String senha = "12345678";

  @Test
  public void testCenario1(){
   UserModel userModel = new UserModel(0L, nome, login, senha);

   when(userRepository.findByLogin(login)).thenReturn(Optional.empty());
   
   when(userRepository.save(userModel)).thenReturn(userModel);
  Optional<UserModel> user = userService.registerUser(userModel);
  assertNotNull(user);
  assertEquals("lairton@gmail.com", user.get().getLogin());
  }

  @Test
  public void testCenario2(){
    UserModel userModel = new UserModel(0L, nome, login, senha);
    when(userRepository.findByLogin(login)).thenReturn(Optional.of(userModel));
    ResponseStatusException thrown = Assertions.assertThrows(ResponseStatusException.class, ()->{
      Optional<UserModel> user = userService.registerUser(userModel);
    });
    Assertions.assertEquals(new ResponseStatusException(HttpStatus.BAD_REQUEST, "O usuário já existe", null).getMessage(), thrown.getMessage());
  }


}
