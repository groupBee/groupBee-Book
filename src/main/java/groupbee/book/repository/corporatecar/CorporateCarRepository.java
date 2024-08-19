package groupbee.book.repository.corporatecar;

import groupbee.book.data.corporatecar.CorporateCarDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CorporateCarRepository extends JpaRepository<CorporateCarDto, Integer> {
    // 추가적인 쿼리 메서드를 정의할 수 있습니다.

    // 리스트 출력
    @NotNull
    List<CorporateCarDto> findAll();
}