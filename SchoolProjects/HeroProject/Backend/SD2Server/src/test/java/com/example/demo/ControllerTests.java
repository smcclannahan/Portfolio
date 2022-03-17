package com.example.demo;


import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import com.example.demo.playerscore.PlayerScore;
import com.example.demo.playerscore.PlayerScoreController;
import com.example.demo.playerscore.PlayerScoreRepository;


@RunWith(SpringRunner.class)
@WebMvcTest(PlayerScoreController.class)
public class ControllerTests  {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	PlayerScoreRepository pSR;
	
	@Test
	public void testPlayerScoreGet() throws Exception
	{
		PlayerScore bob = new PlayerScore();
		bob.setName("bob");
		bob.setLevel(1);
		bob.setScore(3);
		
		List<PlayerScore> allScore = new LinkedList<PlayerScore>();
		allScore.add(bob);
		
		when(pSR.findAll()).thenReturn(allScore);
		
		mvc.perform(get("/score")
				.contentType(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$", hasSize(1)))
		        .andExpect(jsonPath("$[0].name", is(bob.getName())));
		
		
		
		
		
		
	}
	
	@Test
	public void testPlayerScorePost() throws Exception
	{
		
		
		
		mvc.perform(post("/score")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\"level\":2,\"name\":\"ben\",\"score\":689}"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testPlayerScorePostExists() throws Exception
	{
		PlayerScore bob = new PlayerScore();
		bob.setName("ben");
		bob.setLevel(1);
		bob.setScore(3);
		
		Optional<PlayerScore> player = Optional.of(bob);
		
		when(pSR.findByName("ben")).thenReturn(player);
		
		MvcResult result = mvc.perform(post("/score")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\"level\":2,\"name\":\"ben\",\"score\":689}"))
				.andExpect(status().isOk()).andReturn();
		
		String content = result.getResponse().getContentAsString();
		
		assertEquals("Element with id ben alreayd esits", content);
		
		
		
	}
	
	@Test
	public void testPlayerScorePut() throws Exception
	{
		PlayerScore bob = new PlayerScore();
		bob.setName("ben");
		bob.setLevel(1);
		bob.setScore(3);
		
		Optional<PlayerScore> player = Optional.of(bob);
		
		when(pSR.findById("ben")).thenReturn(player);
		
		MvcResult result = mvc.perform(put("/score")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\"level\":2,\"name\":\"ben\",\"score\":689}"))
				.andExpect(status().isOk()).andReturn();
		
		String content = result.getResponse().getContentAsString();
		
		assertEquals("updated", content);
		
	}
	
	@Test
	public void testPlacerScorePutDoesNotExist() throws Exception
	{
		MvcResult result = mvc.perform(put("/score")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\"level\":2,\"name\":\"ben\",\"score\":689}"))
				.andExpect(status().isOk()).andReturn();
		
		String content = result.getResponse().getContentAsString();
		
		assertEquals("no such element", content);
		
	}
	

	

}
