package groupbee.book.service.room;

import groupbee.book.entity.RoomEntity;
import groupbee.book.repository.room.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    public List<RoomEntity> getAllRooms() {
        return roomRepository.findAll();
    }
}
