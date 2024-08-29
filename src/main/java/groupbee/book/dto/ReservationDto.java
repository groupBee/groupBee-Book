package groupbee.book.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {
    @Schema(description = "각, 예약 ID", example = "Long 타입")
    private Long id;

    @Schema(description = "멤버 아이디", example = "jeenukchung")
    private String memberId;

    @Schema(description = "예약 타입", example = "0 = 차량, 1 = 회의실")
    private Long type;

    @Schema(description = "예약 시간", example = "2024-08-28T11:30:00")
    private LocalDateTime startTime;

    @Schema(description = "반납 시간", example = "2024-08-28T12:30:00")
    private LocalDateTime endTime;

    @Schema(description = "사유", example = "출장")
    private String reason;
}
