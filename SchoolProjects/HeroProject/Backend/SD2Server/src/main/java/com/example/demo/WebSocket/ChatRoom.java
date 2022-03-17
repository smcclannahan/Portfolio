package com.example.demo.WebSocket;

import javax.websocket.Session;

public class ChatRoom {

	private int id;

	private String hostname;

	private String clientname;

	private Session hostsession;

	private Session clientsession;

	public int getId() {
		return id;
	}

	public String getHostname() {
		return hostname;
	}

	public String getCLientname() {
		return clientname;
	}

	public ChatRoom(int id, String hostname, Session hostsession) {
		this.id = id;
		this.hostname = hostname;
		this.hostsession = hostsession;
	}

	public Session getHostsession() {
		return hostsession;
	}

	public Session getClientsession() {
		return clientsession;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public void setClientname(String clientname) {
		this.clientname = clientname;
	}

	public void setHostsession(Session hostsession) {
		this.hostsession = hostsession;
	}

	public void setClientsession(Session clientsession) {
		this.clientsession = clientsession;
	}

	public Session getSessionFromName(String name) {

		if (name == hostname) {
			return hostsession;
		} else if (name == clientname) {
			return clientsession;
		}

		return null;
	}

	public Session getSessionFromOtherSession(Session session) {
		if (session == hostsession) {
			return clientsession;
		} else if (session == clientsession) {
			return hostsession;
		}

		return null;
	}

	public String getNameFromSession(Session session) {
		if (session == hostsession) {
			return clientname;
		} else if (session == clientsession) {
			return hostname;
		}

		return null;

	}

}
