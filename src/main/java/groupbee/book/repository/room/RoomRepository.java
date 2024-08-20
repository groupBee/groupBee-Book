package groupbee.book.repository.room;

import groupbee.book.entity.RoomEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
}
