package groupbee.book.service.room;

import groupbee.book.data.room.RoomDto;
import groupbee.book.repository.room.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<RoomDto> getAllRooms() {
        return roomRepository.findAll();
    }
}
