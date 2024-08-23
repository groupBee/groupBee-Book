package groupbee.book.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@ToString
@Builder
@Table(name = "corporate_car")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CorporateCarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "car_id", length = Integer.MAX_VALUE)
    private String carId;

    @Column(name = "type", length = Integer.MAX_VALUE)
    private String type;

    @Column(name = "photo", length = Integer.MAX_VALUE)
    private String photo;
}