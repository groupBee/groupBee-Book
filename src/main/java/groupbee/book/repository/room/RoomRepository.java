package groupbee.book.repository.room;

import groupbee.book.data.room.RoomDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<RoomDto, Integer> {

    // 리스트 출력
    @NotNull
    List<RoomDto> findAll();
}
