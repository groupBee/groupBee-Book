package groupbee.book.data.corporatecar;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "corporate_car")
public class CorporateCarDto {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "car_id", length = Integer.MAX_VALUE)
    private String carId;


    @Column(name = "type", length = Integer.MAX_VALUE)
    private String type;

    @Column(name = "photo", length = Integer.MAX_VALUE)
    private String photo;

}