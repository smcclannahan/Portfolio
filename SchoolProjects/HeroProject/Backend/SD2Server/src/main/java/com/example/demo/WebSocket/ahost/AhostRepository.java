package com.example.demo.WebSocket.ahost;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AhostRepository extends JpaRepository<Ahost, Integer> {

	public Optional<Ahost> findByHostname(String Hostname);

}
