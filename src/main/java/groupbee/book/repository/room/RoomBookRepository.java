package groupbee.book.repository.room;

import groupbee.book.data.room.RoomBookDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomBookRepository extends JpaRepository<RoomBookDto, Integer> {

    // 리스트 출력
    @NotNull
    List<RoomBookDto> findAll();
}
