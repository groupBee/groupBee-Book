package groupbee.book.repository.corporatecar;

import groupbee.book.entity.CorporateCarEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CorporateCarRepository extends JpaRepository<CorporateCarEntity, Long> {
}