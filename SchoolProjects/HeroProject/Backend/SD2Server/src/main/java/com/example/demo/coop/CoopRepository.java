package com.example.demo.coop;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.coop.Coop;

@Repository
public interface CoopRepository extends JpaRepository<Coop, String> {

	public List<Coop> findByPlayer1(String Player1);

	public List<Coop> findByPlayer2(String Player2);

}
