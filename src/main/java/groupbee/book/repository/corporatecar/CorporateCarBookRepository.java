package groupbee.book.repository.corporatecar;

import groupbee.book.entity.CorporateCarBookEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CorporateCarBookRepository extends JpaRepository<CorporateCarBookEntity, Long> {
    @Query("SELECT COUNT(c) > 0 FROM CorporateCarBookEntity c " +
            "WHERE c.corporateCarId = :corporateCarId " +
            "AND (c.rentDay < :returnDay AND c.returnDay > :rentDay)")
    boolean existsByCorporateCarIdAnd(@Param("corporateCarId") Long corporateCarId,
                                      @Param("rentDay") LocalDateTime rentDay,
                                      @Param("returnDay") LocalDateTime returnDay);

    List<CorporateCarBookEntity> findByMemberId(String memberId);
}