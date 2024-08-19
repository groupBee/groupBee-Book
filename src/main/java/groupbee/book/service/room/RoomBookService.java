package groupbee.book.service.room;

import groupbee.book.data.corporatecar.CorporateCarBookDto;
import groupbee.book.data.room.RoomBookDto;
import groupbee.book.repository.room.RoomBookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoomBookService {

    private RoomBookRepository roomBookRepository;

    public void insertRooms(RoomBookDto dto)
    {
        roomBookRepository.save(dto);
    }

    public List<RoomBookDto> getAllRoomBook() {
        return roomBookRepository.findAll();
    }

    // 예약 삭제
    public void deleteRoomBook(Integer id) {
        roomBookRepository.deleteById(id);
    }

    //업데이트
    public void updateRoomBook(Integer id, RoomBookDto dto) {
        // 예약이 존재하는지 확인
        if (roomBookRepository.existsById(id)) {
            // 예약의 ID를 설정하고 저장
            dto.setId(id);
            roomBookRepository.save(dto);
        } else {
            // 예약이 존재하지 않으면 예외 발생
            throw new RuntimeException("Booking not found with id: " + id);
        }
    }
}
