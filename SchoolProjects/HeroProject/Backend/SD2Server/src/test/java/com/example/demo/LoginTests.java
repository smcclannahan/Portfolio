package com.example.demo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.demo.playercheckin.PlayerCheckin;
import com.example.demo.playercheckin.PlayerCheckinRepository;
import com.example.demo.playerlogin.PlayerLogin;
import com.example.demo.playerlogin.PlayerLoginController;
import com.example.demo.playerlogin.PlayerLoginRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(PlayerLoginController.class)
public class LoginTests {

@Autowired
private MockMvc mvc;

@MockBean
PlayerLoginRepository pLR;

@MockBean
PlayerCheckinRepository pCI;


public PlayerLogin bob;
public PlayerCheckin cob;



@Before
public void setUp()
{
	bob = new PlayerLogin();
	cob = new PlayerCheckin();
	
	bob.setName("bob");
	cob.setName("bob");
	bob.setPassword("abc");
	bob.setPlayerCheckin(cob);
	cob.setIsLoggedIn(false);
	cob.setSkin("p1");
	cob.setPlayerLogin(bob);
	
	
	
	
}

@Test
public void testNewPlayer() throws Exception
{
	Optional<PlayerLogin> player = Optional.empty();
	
	when(pLR.findByName("bob")).thenReturn(player);
	
	MvcResult result = mvc.perform(post("/newPlayer")
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.content("{\"name\":bob,\"password\":abc}"))
			.andExpect(status().isOk()).andReturn();
	
	String content = result.getResponse().getContentAsString();
	
	assertEquals("welcome", content);
	
	
}

@Test
public void testNewPlayerExists() throws Exception
{
Optional<PlayerLogin> player = Optional.of(bob);
	
	when(pLR.findByName("bob")).thenReturn(player);
	
	MvcResult result = mvc.perform(post("/newPlayer")
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.content("{\"name\":bob,\"password\":abc}"))
			.andExpect(status().isOk()).andReturn();
	
	String content = result.getResponse().getContentAsString();
	
	assertEquals("That user name is alrady taken", content);
	
  
}

@Test
public void testLogin() throws Exception
{
Optional<PlayerLogin> player = Optional.of(bob);
Optional<PlayerCheckin> playerC = Optional.of(cob);
	
	when(pLR.findByName("bob")).thenReturn(player);
	when(pCI.findById("bob")).thenReturn(playerC);
	
	MvcResult result = mvc.perform(put("/login")
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.content("{\"name\":bob,\"password\":abc}"))
			.andExpect(status().isOk()).andReturn();
	
	String content = result.getResponse().getContentAsString();
	
	assertEquals("bob logged in.", content);
	
	
	
}


	
	
	
}
