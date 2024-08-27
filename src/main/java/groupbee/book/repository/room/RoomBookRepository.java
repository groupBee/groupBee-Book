package groupbee.book.repository.room;

import groupbee.book.entity.RoomBookEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RoomBookRepository extends JpaRepository<RoomBookEntity, Long> {
    @Query("SELECT COUNT(r) > 0 FROM RoomBookEntity r " +
            "WHERE r.roomId = :roomId " +
            "AND (:enter < r.leave AND :leave > r.enter)")
    boolean existsByRoomIdAnd(@Param("roomId") Long roomId,
                              @Param("enter") LocalDateTime enter,
                              @Param("leave") LocalDateTime leave);
}
