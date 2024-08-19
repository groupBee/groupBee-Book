package groupbee.book.data.corporatecar;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "corporate_car_book")
public class CorporateCarBookDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "corporate_car_id")
    private Integer corporateCarId;

    @Column(name = "member_id")
    private Integer memberId;

    @Column(name = "rent_day")
    private LocalDateTime rentDay;

    @Column(name = "return_day")
    private LocalDateTime returnDay;

    @Column(name = "reason", length = Integer.MAX_VALUE)
    private String reason;

}