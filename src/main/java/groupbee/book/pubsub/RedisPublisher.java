package groupbee.book.pubsub;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import groupbee.book.dto.CarBookDto;
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
    private final ChannelTopic topic;
    private final ObjectMapper objectMapper;

    public void publish(CarBookDto carBookDto) {
        try {
            String messageJson = objectMapper.writeValueAsString(carBookDto);
            log.info("Publishing message: {}", messageJson);
            redisTemplate.convertAndSend(topic.getTopic(), messageJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
