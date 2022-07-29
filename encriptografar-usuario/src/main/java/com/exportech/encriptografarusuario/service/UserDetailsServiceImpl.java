package com.exportech.encriptografarusuario.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.exportech.encriptografarusuario.data.UserDetailsData;
import com.exportech.encriptografarusuario.model.UserModel;
import com.exportech.encriptografarusuario.repository.UserRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService{

  private final UserRepository userRepository;


  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<UserModel> userModel = userRepository.findByLogin(username);
    if(userModel.isEmpty()){
      throw new UsernameNotFoundException("Username ["+username+"] not found!!");
    }
    return new UserDetailsData(userModel);
  }
  
}
