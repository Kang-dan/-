package com.ssafy.enjoytrip.configuration;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private static final ConcurrentHashMap<String, WebSocketSession> CLIENTS = new ConcurrentHashMap<String, WebSocketSession>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {    	
        CLIENTS.put(session.getId(), session);
        System.out.println("사용자가 웹소켓에 접속함 : " + session);
        System.out.println("사용자아이디 : " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        CLIENTS.remove(session.getId());
        System.out.println("사용자가 웹소켓을 종료함 : " + session);
        System.out.println("사용자아이디 : " + session.getId());
    }
    
    public void changeMessage() {
    	CLIENTS.entrySet().forEach( arg->{
            if(true) { 
                try {
                	System.out.println("메시지를 전달함");
                    arg.getValue().sendMessage(new TextMessage("테스트메시지"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String id = session.getId();  //메시지를 보낸 아이디
        System.out.println("사용자가 메세지를 보냄 : " + id + " : " + message);
        CLIENTS.entrySet().forEach( arg->{
            if(true) {  //같은 아이디가 아니면 메시지를 전달합니다.
                try {
                	System.out.println("메시지를 전달함");
                    arg.getValue().sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}