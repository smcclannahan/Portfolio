package com.example.demo.WebSocket;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.pizza.PizzaRepository;
import com.example.demo.pizza.Pizza;
import java.lang.Integer;

//@author Vamsi Krishna Calpakkam


@ServerEndpoint(value="/websocket/{username}",configurator = CustomConfigurator.class)
@Component
public class Serversocket {
	
	@Autowired
	PizzaRepository PizzasRepositroy;
	
    private static Map<Session, String> sessionUsernameMap = new HashMap<>();
    private static Map<String, Session> usernameSessionMap = new HashMap<>();
    
    private final Logger logger = LoggerFactory.getLogger(Serversocket.class);
    
    @OnOpen
    public void onOpen(
    	      Session session, 
    	      @PathParam("username") String username) throws IOException 
    {
        logger.info("Entered into Open");
        
        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);
        
        String message="User:" + username + " has Joined the Chat";
        	broadcast(message);
        	
		
    }
 
    @OnMessage
    public void onMessage(Session session, String message) throws IOException 
    {
        // Handle new messages
    	logger.info("Entered into Message: Got Message:"+message);
    	String username = sessionUsernameMap.get(session);
    	
    	//if (message.startsWith("@")) // Direct message to a user using the format "@username <message>"
    	//{
    		//String destUsername = message.split(" ")[0].substring(1); // don't do this in your code!
    		//sendMessageToPArticularUser(destUsername, "[DM] " + username + ": " + message);
    		//sendMessageToPArticularUser(username, "[DM] " + username + ": " + message);
    	//}
    	//else // Message to whole chat
    	//{
	    	broadcast(username + ": " + message);
    	//}
	    	
	    	Optional<Pizza> results = PizzasRepositroy.findById(1);
	    	logger.info("here");
	    	if(results.isPresent())
	    	{
	    		logger.info("inside");
	    	Pizza peparoni = (Pizza) results.get();
	    	logger.info("here2");
	    	String crust = peparoni.getCrust();
	    	logger.info("here3");
	    	broadcast(crust);
	    	int i = Integer.parseInt(crust);
	    	i++;
	    	Integer ini = new Integer(i);
	    	peparoni.setCrust(ini.toString());
	    	PizzasRepositroy.save(peparoni);
	    	}
	    	logger.info("madeit");
	    	
	    	
    }
 
    @OnClose
    public void onClose(Session session) throws IOException
    {
    	logger.info("Entered into Close");
    	
    	String username = sessionUsernameMap.get(session);
    	sessionUsernameMap.remove(session);
    	usernameSessionMap.remove(username);
        
    	String message= username + " disconnected";
        broadcast(message);
    }
 
    @OnError
    public void onError(Session session, Throwable throwable) 
    {
        // Do error handling here
    	logger.info("Entered into Error");
    }
    
	private void sendMessageToPArticularUser(String username, String message) 
    {	
    	try {
    		usernameSessionMap.get(username).getBasicRemote().sendText(message);
        } catch (IOException e) {
        	logger.info("Exception: " + e.getMessage().toString());
            e.printStackTrace();
        }
    }
    
    private static void broadcast(String message) 
    	      throws IOException 
    {	  
    	sessionUsernameMap.forEach((session, username) -> {
    		synchronized (session) {
	            try {
	                session.getBasicRemote().sendText(message);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    });
	}


}
