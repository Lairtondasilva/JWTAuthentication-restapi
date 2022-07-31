package com.exportech.encriptografarusuario.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.exportech.encriptografarusuario.model.UserModel;
import com.exportech.encriptografarusuario.repository.UserRepository;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder encoder;

  public UserService(UserRepository userRepository, PasswordEncoder encoder){
    this.userRepository = userRepository;
    this.encoder = encoder;
  }
  
  public Optional<UserModel> registerUser(UserModel userModel){
     if(userRepository.findByLogin(userModel.getLogin()).isPresent()){
       throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O usuário já existe", null);
     }
     
    userModel.setPassword(encoder.encode(userModel.getPassword()));
    return Optional.of(userRepository.save(userModel));
  }

  public boolean passwordValidator (String login, String password){

    Optional<UserModel> opUser = userRepository.findByLogin(login);
    if(opUser.isEmpty()){
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login não existe", null);
    }

    UserModel user = opUser.get();
    boolean valid = encoder.matches(password, user.getPassword());
    return valid;
  }
}
