package groupbee.book.dto;

import groupbee.book.entity.CorporateCarBookEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CarBookDto {
    private Long id;
    private String memberId;
    private Long corporateCarId;
    private LocalDateTime rentDay;
    private LocalDateTime returnDay;
    private String reason;
    private String eventType;

    // DTO 와 Entity 간 변환 메서드
    public static CarBookDto fromEntity(CorporateCarBookEntity entity) {
        return new CarBookDto(
                entity.getId(),
                entity.getMemberId(),
                entity.getCorporateCarId(),
                entity.getRentDay(),
                entity.getReturnDay(),
                entity.getReason(),
                entity.getEventType()
        );
    }
}
