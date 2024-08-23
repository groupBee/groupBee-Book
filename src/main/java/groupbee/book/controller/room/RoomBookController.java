package groupbee.book.controller.room;

import groupbee.book.entity.RoomBookEntity;
import groupbee.book.service.room.RoomBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
public class RoomBookController {

    private final RoomBookService roomBookService;

    @PostMapping("/insert")
    public void insertRooms(@RequestBody RoomBookEntity dto)
    {
        roomBookService.insertRooms(dto);
    }

    @GetMapping("/booklist")
    public List<RoomBookEntity> listRoomBook() {
        return roomBookService.getAllRoomBook();
    }

    // 예약 삭제
    @DeleteMapping("/delete/{id}")
    public void deleteRoomBook(@PathVariable Long id) {
        roomBookService.deleteRoomBook(id);
    }

    //업데이트
    @PutMapping("/update/{id}")
    public void updateRoomBook(@PathVariable Long id, @RequestBody RoomBookEntity dto) {
        roomBookService.updateRoomBook(id, dto);
    }
}
