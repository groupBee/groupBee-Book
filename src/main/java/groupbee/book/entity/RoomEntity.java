package groupbee.book.entity.room;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "room")
public class RoomEntity {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = Integer.MAX_VALUE)
    private String name;

    @Column(name = "rant")
    private Boolean rant;

    @Column(name = "photo", length = Integer.MAX_VALUE)
    private String photo;

}