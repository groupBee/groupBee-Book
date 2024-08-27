package groupbee.book.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import groupbee.book.entity.RoomBookEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomBookDto {
    private Long id;
    private Long roomId;
    private String memberId;
    private LocalDateTime enter;
    private LocalDateTime leave;
    private String purpose;
    private String eventType;

    public static RoomBookDto fromEntity(RoomBookEntity entity) {
        return new RoomBookDto(
                entity.getId(),
                entity.getRoomId(),
                entity.getMemberId(),
                entity.getEnter(),
                entity.getLeave(),
                entity.getPurpose(),
                entity.getEventType()
        );
    }
}
