package com.prac.prac.chat;

import com.prac.prac.chat.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

// import 생략...

@RequiredArgsConstructor
@Controller
public class ChatController {

    private  final  ChatService chatService;
    /**
     * websocket에서  "/pub/chat/message"로 들어오는 메시징을 처리한다.
     *
     */
    @MessageMapping("/chat/message")
    public void message(ChatMessage chatMessage) {   //ws.send()에서 JSON.stringfy를 통해 자동으로 ChatMessage 형태로 반환된다.
        if(!chatService.isRegisteredChannelTopic(chatMessage.getRoomId())){ //등록이 안되어있다면 등록해주자.
               chatService.registerChannelTopic(chatMessage.getRoomId());
        }
        if (ChatMessage.MessageType.ENTER.equals(chatMessage.getType())) {
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장하셨습니다.");
        }
        chatService.publish(chatMessage);   //chatMessage에

        
    }


}