package groupbee.book.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@ToString
@Builder
@Table(name = "corporate_car_book")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CorporateCarBookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "corporate_car_id")
    private Long corporateCarId;

    @Column(name = "member_id")
    private String memberId;

    @Column(name = "rent_day")
    private LocalDateTime rentDay;

    @Column(name = "return_day")
    private LocalDateTime returnDay;

    @Column(name = "reason", length = Integer.MAX_VALUE)
    private String reason;

    @Column(name = "calendar_id")
    private Long calendarId;

    @Column(name = "event_type")
    private String eventType;
}