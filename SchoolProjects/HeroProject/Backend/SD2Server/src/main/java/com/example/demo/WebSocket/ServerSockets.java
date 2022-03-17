package com.example.demo.WebSocket;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
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
import org.springframework.stereotype.Component;

/**
 * 
 * @author caleb L meyer classed used for websockets accessed by endpoint
 *         /SD2Socket/{username} First player to access will get message FIRST
 *         when the second player joins first player will receive message JOINED
 *         The second player will get message SECOND All further attempts will
 *         be immediately closed until first two people leave
 */
@ServerEndpoint(value = "/SD2Socket/{username}", configurator = CustomConfigurator.class)
@Component
public class ServerSockets {

	private static Map<Session, String> sessionUsernameMap = new HashMap<>();
	private static Map<String, Session> usernameSessionMap = new HashMap<>();

	private static LinkedList<Session> sessionList = new LinkedList<>();

	private final Logger logger = LoggerFactory.getLogger(ServerSockets.class);

	@OnOpen
	public void onOpen(Session session, @PathParam("username") String username) throws IOException {
		logger.info("new connection");

		if (sessionList.size() == 0) {
			sessionUsernameMap.put(session, username);
			usernameSessionMap.put(username, session);
			sessionList.add(session);
			session.getBasicRemote().sendText("FIRST");

		} else if (sessionList.size() == 1) {
			sessionUsernameMap.put(session, username);
			usernameSessionMap.put(username, session);
			sessionList.add(session);
			session.getBasicRemote().sendText("SECOND");
			sessionList.peek().getBasicRemote().sendText("JOINED");
		} else {
			session.getBasicRemote().sendText("SERVER FULL");
			session.close();

		}

	}

	@OnMessage
	public void onMessage(Session session, String message) throws IOException {
		logger.info("message sent");
		int index = sessionList.indexOf(session);
		if (index == 0) {
			sessionList.get(1).getBasicRemote().sendText(message);
		} else {
			sessionList.get(0).getBasicRemote().sendText(message);
		}

	}

	@OnClose
	public void onClose(Session session) throws IOException {
		if (sessionList.contains(session)) {
			String username = sessionUsernameMap.get(session);
			sessionUsernameMap.remove(session);
			usernameSessionMap.remove(username);
			sessionList.remove(session);
			if (sessionList.size() == 1) {
				Session first = sessionList.element();
				first.getBasicRemote().sendText("DISCONNECTED");
				first.close();
			}

		}
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		logger.info("Error in socket");
	}

}
