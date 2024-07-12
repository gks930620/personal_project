package com.prac.prac.chat;


import com.prac.prac.chat.model.ChatMessage;
import com.prac.prac.chat.pubsub.RedisSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ChatService {
    // 구독 처리 서비스
    private final RedisSubscriber redisSubscriber;
    // 채팅방(topic)에 발행되는 메시지를 처리할 Listner. config에서 등록한거.
    private final RedisMessageListenerContainer redisMessageListener;

    private Map<String, ChannelTopic> channelTopicMap=new HashMap<>();
    //서버가 재시작되면 channelTopicMap은 초기화되겠지만
    //DB에 ChatRoom만 남아있으면   밑의 enterChatRoom()을 통해
    // 해당 ChatRoom에 다시 접속할 수 있기때문에 굳이 ChannelTopic은 DB에 저장안함
    /*TODO : ChannelTopic은 Serialize 안되어있어서 redisDB저장 x  DB에 억지로 저장하는것보단 Map으로 사용하는게 맞는듯.*/
    private final RedisTemplate<String, Object> redisTemplate;



    public boolean isRegisteredChannelTopic(String roomId){
        return channelTopicMap.get(roomId) !=null ; //처음왔으면 false
    }


    public void registerChannelTopic  (String roomId) {
        ChannelTopic channelTopic = channelTopicMap.get(roomId); //
        if (channelTopic == null) { //처음 들어오는 사람의 경우
            channelTopic = new ChannelTopic(roomId);
            redisMessageListener.addMessageListener(redisSubscriber, channelTopic);  //listener에 channelTopic 설정.
            channelTopicMap.put(roomId, channelTopic);
        }
    }

    public void publish(ChatMessage chatMessage){
        ChannelTopic channelTopic=channelTopicMap.get(chatMessage.getRoomId());  //내가 접속한 방 얻기
        redisTemplate.convertAndSend(channelTopic.getTopic(), chatMessage);
        // 방에다가 publish 하는 메소드  이 후sucscriber의 onMessage가 실행됨 자동으로.
    }

}
