package com.example.demo.playerlogin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.example.demo.playerlogin.PlayerLogin;

@Repository
public interface PlayerLoginRepository extends JpaRepository<PlayerLogin, String> {

	public Optional<PlayerLogin> findByName(String name);

}
