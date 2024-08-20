package groupbee.book.repository.corporatecar;

import groupbee.book.entity.CorporateCarBookEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CorporateCarBookRepository extends JpaRepository<CorporateCarBookEntity, Long> {
}