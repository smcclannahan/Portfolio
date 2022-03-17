package com.example.demo.pizza;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import com.example.demo.pizza.Pizza;



import com.example.demo.pizza.PizzaController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@RestController
public class PizzaController {
	
	@Autowired
	PizzaRepository PizzasRepositroy;
	
	private final Logger logger = LoggerFactory.getLogger(PizzaController.class);
	
	 @RequestMapping(method = RequestMethod.POST, path = "/pizza/new")
	    public String savePizza(Pizza pizza) {
	        PizzasRepositroy.save(pizza);
	        return "New pizza Saved";
	    }
	 
	 @RequestMapping(method = RequestMethod.GET, path = "/pizza")
	    public List<Pizza> getallPizza() {
	        logger.info("Entered into Controller Layer");
	        List<Pizza> results = PizzasRepositroy.findAll();
	        logger.info("Number of Records Fetched:" + results.size());
	        return results;
	    }
	 
	 @RequestMapping(method = RequestMethod.GET, path = "/pizza/{pizzaId}")
	    public Optional<Pizza> findOwnerById(@PathVariable("pizzaId") int id) {
	        logger.info("Entered into Controller Layer");
	        Optional<Pizza> results = PizzasRepositroy.findById(id);
	        return results;
	    }
	

}
