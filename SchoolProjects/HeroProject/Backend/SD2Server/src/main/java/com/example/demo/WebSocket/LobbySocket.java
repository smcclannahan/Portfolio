package com.example.demo.WebSocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

import com.example.demo.WebSocket.ahost.AhostRepository;
import com.example.demo.playerlogin.PlayerLoginRepository;

@ServerEndpoint(value = "/LobbySocket/{roomID}/{username}", configurator = CustomConfigurator.class)
@Component
public class LobbySocket {

	@Autowired
	AhostRepository ahostRepositry;

	@Autowired
	PlayerLoginRepository playerLoginRepsitory;

	private static Map<Integer, ChatRoom> chatroomFromId = new HashMap<Integer, ChatRoom>();

	private final Logger logger = LoggerFactory.getLogger(LobbySocket.class);

	@OnOpen
	public void onOpen(Session session, @PathParam("roomID") int roomID, @PathParam("username") String username)
			throws IOException {
		logger.info("new session");
		if (ahostRepositry.findById(roomID).isPresent()) {
			if (!chatroomFromId.containsKey(roomID)) {
				ChatRoom newRoom = new ChatRoom(roomID, username, session);
				chatroomFromId.put(roomID, newRoom);
				logger.info("new host");
			} else {
				logger.info("new client");
				ChatRoom oldRoom = chatroomFromId.get(roomID);
				oldRoom.setClientname(username);
				oldRoom.setClientsession(session);
				logger.info("new client added");
				oldRoom.getHostsession().getBasicRemote().sendText("JOINED: " + username);
				logger.info("host alerted");
				ahostRepositry.deleteById(roomID);

			}
		} else {
			logger.info("full");
			session.getBasicRemote().sendText("Room full try again");
			session.close();
		}

	}

	@OnMessage
	public void onMessage(Session session, String message, @PathParam("roomID") int roomID) throws IOException {
		ChatRoom room = chatroomFromId.get(roomID);
		room.getSessionFromOtherSession(session).getBasicRemote().sendText(message);
		logger.info("message");

	}

	@OnClose
	public void onCLose(Session session, @PathParam("roomID") int roomID, @PathParam("username") String username)
			throws IOException {
		logger.info("closing");
		if (chatroomFromId.containsKey(roomID)) {
			if (session == chatroomFromId.get(roomID).getClientsession()
					|| session == chatroomFromId.get(roomID).getHostsession()) {
				logger.info("closing room");
				// chatroomFromId.get(roomID).getSessionFromOtherSession(session).close();
				ChatRoom closingRoom = chatroomFromId.remove(roomID);
				closingRoom.getSessionFromOtherSession(session).close();

				if (ahostRepositry.findById(roomID).isPresent()) {					
					ahostRepositry.deleteById(roomID);
				}
			}
		}

	}

	@OnError
	public void onError(Session session, Throwable throwable) {

	}

}
