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

import com.example.demo.coop.Coop;
import com.example.demo.coop.CoopController;
import com.example.demo.coop.CoopRepository;


import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(CoopController.class)
public class CoopControlerTests {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	CoopRepository cR;
	
	@Test
	public void testCoopGetAll() throws Exception
	{
		Coop benjerry = new Coop();
		benjerry.setCombined("ben jerry");
		benjerry.setLevel(1);
		benjerry.setScore(5);
		
		List<Coop> allScore = new LinkedList<Coop>();
		allScore.add(benjerry);
		
		when(cR.findAll()).thenReturn(allScore);
		
		 mvc.perform(get("/coopScore")
				.contentType(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$", hasSize(1)))
		        .andExpect(jsonPath("$[0].combined", is(benjerry.getCombined())));
		
		
	}
	
	@Test
	public void testCoopPost() throws Exception
	{
		mvc.perform(post("/coopScore")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\"coopname\":\"ben jerry\",\"score\":689,\"level\":5}"))
				.andExpect(status().isOk());
		
		
	}
	
	@Test
	public void testCoopPostExists() throws Exception
	{
		Coop benjerry = new Coop();
		benjerry.setCombined("ben jerry");
		benjerry.setLevel(1);
		benjerry.setScore(5);
		
		Optional<Coop> here =  Optional.of(benjerry);
		
		when(cR.findById("ben jerry")).thenReturn(here);
		
		MvcResult result = mvc.perform(post("/coopScore")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\"coopname\":\"ben jerry\",\"score\":689,\"level\":5}"))
				.andExpect(status().isOk()).andReturn();
		
		String content = result.getResponse().getContentAsString();
		
		assertEquals("already Exist",content);
		
		
		
		
		
	}
	
	@Test
	public void testCoopPut() throws Exception
	{
		Coop benjerry = new Coop();
		benjerry.setCombined("ben jerry");
		benjerry.setLevel(1);
		benjerry.setScore(5);
		
		Optional<Coop> here =  Optional.of(benjerry);
		
		when(cR.findById("ben jerry")).thenReturn(here);
		
		MvcResult result = mvc.perform(put("/coopScore")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\"coopname\":\"ben jerry\",\"score\":689,\"level\":5}"))
				.andExpect(status().isOk()).andReturn();
		
		String content = result.getResponse().getContentAsString();
		
		assertEquals("updated",content);
		
	}
	
	@Test
	public void testCoopPutDoesNotExist() throws Exception
	{
		MvcResult result = mvc.perform(put("/coopScore")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content("{\"coopname\":\"ben jerry\",\"score\":689,\"level\":5}"))
				.andExpect(status().isOk()).andReturn();
		
		String content = result.getResponse().getContentAsString();
		
		assertEquals("no Such Element",content);
		
	}
	
	@Test
	public void testSpecificPlayer() throws Exception
	{
		Coop benjerry = new Coop();
		benjerry.setCombined("ben jerry");
		benjerry.setLevel(1);
		benjerry.setScore(5);
		
		List<Coop> allScore = new LinkedList<Coop>();
		allScore.add(benjerry);
		
		when(cR.findByPlayer1("ben")).thenReturn(allScore);
		
		
		 mvc.perform(get("/coopScore/ben")
				.contentType(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$", hasSize(1)))
		        .andExpect(jsonPath("$[0].combined", is(benjerry.getCombined())));
		
		
		
	}
	
	
	

}
