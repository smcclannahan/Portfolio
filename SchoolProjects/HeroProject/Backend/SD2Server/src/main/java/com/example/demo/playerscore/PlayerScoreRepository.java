package com.example.demo.playerscore;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.example.demo.playerscore.PlayerScore;

@Repository
public interface PlayerScoreRepository extends JpaRepository<PlayerScore, String> {

	public Optional<PlayerScore> findByName(String name);

}
