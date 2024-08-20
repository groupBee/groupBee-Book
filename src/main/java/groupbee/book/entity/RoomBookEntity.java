package groupbee.book.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@ToString
@Builder
@Table(name = "room_book")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RoomBookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "member_id")
    private String memberId;

    @Column(name = "enter")
    private LocalDateTime enter;

    @Column(name = "leave")
    private LocalDateTime leave;

    @Column(name = "purpose", length = Integer.MAX_VALUE)
    private String purpose;
}