package groupbee.book.pubsub;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import groupbee.book.dto.CarBookDto;
import groupbee.book.dto.RoomBookDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisPublisher {
    private final RedisTemplate<String, Object> redisTemplate; // <key, value>
    private final ChannelTopic carBookTopic;
    private final ChannelTopic roomBookTopic;
    private final ObjectMapper objectMapper;

    // 자동차 예약 PUB
    public void publishToCarBook(CarBookDto carBookDto) {
        try {
            String messageJson = objectMapper.writeValueAsString(carBookDto);
            log.info("Publishing message: {}", messageJson);
            redisTemplate.convertAndSend(carBookTopic.getTopic(), messageJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    // 회의실 예약 PUB
    public void publishToRoomBook(RoomBookDto roomBookDto) {
        try {
            String messageJson = objectMapper.writeValueAsString(roomBookDto);
            log.info("Publishing message: {}", messageJson);
            redisTemplate.convertAndSend(roomBookTopic.getTopic(), messageJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
