package com.prac.prac.chat.pubsub;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prac.prac.chat.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedisSubscriber implements MessageListener {

    private final ObjectMapper objectMapper;
    private final RedisTemplate redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;



    /**
     * Redis에서 메시지가 발행(publish)되면 대기하고 있던 onMessage가 해당 메시지를 받아 처리한다.
     * 단순히  redis에서 pub하면 여기가 실행됨
     *
     * redis의 channelTopic= ChatRoom    1:1
     * redis의 Subscriber는 ChatRoom(channelTopic)에 한명
     *
     * publish 될 때  Subscriber 한명에게만 메세지 전달 (onMessage)
     * onMessage 메소드 실행 중 webscoket으로 연결된 user들 전부에게 message전달 (convertAndSend)
     *
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String publishMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
            ChatMessage roomMessage = objectMapper.readValue(publishMessage, ChatMessage.class);

            // Websocket 구독자에게 채팅 메시지 Send (실제 websocket으로 message 전달하는 기능)
            messagingTemplate.convertAndSend("/sub/chat/room/" + roomMessage.getRoomId(), roomMessage);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}