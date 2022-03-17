package com.example.demo.playercheckin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.playercheckin.PlayerCheckin;

@Repository
public interface PlayerCheckinRepository extends JpaRepository<PlayerCheckin, String> {

}
