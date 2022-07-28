package com.exportech.encriptografarusuario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exportech.encriptografarusuario.model.UserModel;

public interface userRepository extends JpaRepository<UserModel, Long> {
  public Optional<UserModel> findByLogin();
}
