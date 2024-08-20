package groupbee.book.entity.room;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "room_book")
public class RoomBookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "room_id")
    private Integer roomId;

    @Column(name = "member_id")
    private Integer memberId;

    @Column(name = "enter")
    private LocalDateTime enter;

    @Column(name = "leave")
    private LocalDateTime leave;

    @Column(name = "purpose", length = Integer.MAX_VALUE)
    private String purpose;

}