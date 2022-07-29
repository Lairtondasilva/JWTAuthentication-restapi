package com.exportech.encriptografarusuario.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.exportech.encriptografarusuario.model.UserModel;

public class UserDetailsData implements UserDetails{

  private final Optional<UserModel> userModel;

  public UserDetailsData(Optional<UserModel> userModel) {
    this.userModel = userModel;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // TODO Auto-generated method stub
    return new ArrayList<>();
  }

  @Override
  public String getPassword() {
    // TODO Auto-generated method stub
    return userModel.orElse(new UserModel()).getPassword();
  }

  @Override
  public String getUsername() {
    // TODO Auto-generated method stub
    return userModel.orElse(new UserModel()).getLogin();
  }

  @Override
  public boolean isAccountNonExpired() {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    // TODO Auto-generated method stub
    return true;
  }

  @Override
  public boolean isEnabled() {
    // TODO Auto-generated method stub
    return true;
  }
  
}
