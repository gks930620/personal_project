package com.prac.prac.chat;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker  // websocket서버로서 동작하겠다.. 근데  이제 Stomp가 적용돼서 MessageBroker로서 
public class WebSockConfig implements WebSocketMessageBrokerConfigurer { //implements된 interface도 MessageBroker기능이 있는걸로

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/sub");  //ws.subscribe() 의 uri
        config.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp").setAllowedOriginPatterns("*")   //setAllow 메소드이름이 바뀌었다.
                .withSockJS();
    }
}