package groupbee.book.repository.corporatecar;

import groupbee.book.data.corporatecar.CorporateCarBookDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CorporateCarBookRepository extends JpaRepository<CorporateCarBookDto, Integer> {

    // 리스트 출력
    @NotNull
    List<CorporateCarBookDto> findAll();
}