package groupbee.book.pubsub;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import groupbee.book.service.corporatecar.CorporateCarBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarBookSubscriber implements MessageListener {
    private final ObjectMapper objectMapper; // JSON 변환을 위한 ObjectMapper
    private final CorporateCarBookService corporateCarBookService;

    @Override
    public void onMessage(@NotNull Message message, byte[] pattern) {
        String channel = new String(pattern); // 채널 이름을 추출
        String messageBody = new String(message.getBody()); // 메세지 본문을 추출

        log.info("Channel: {}, Message: {}", channel, messageBody);
        try {
            JsonNode rootNode = objectMapper.readTree(messageBody);
            String eventType = rootNode.get("eventType").asText();

            Long corporateCarId = rootNode.get("corporateCarId").asLong();
            //corporateCarBookService.deleteById(corporateCarId);
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException: {}", e.getMessage());
        }
    }
}
