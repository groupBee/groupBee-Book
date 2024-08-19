package groupbee.book.controller.room;

import groupbee.book.data.corporatecar.CorporateCarBookDto;
import groupbee.book.data.room.RoomBookDto;
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
    public void insertRooms(@RequestBody RoomBookDto dto)
    {
        roomBookService.insertRooms(dto);
    }

    @GetMapping("/booklist")
    public List<RoomBookDto> listRoomBook() {
        return roomBookService.getAllRoomBook();
    }

    // 예약 삭제
    @DeleteMapping("/delete/{id}")
    public void deleteRoomBook(@PathVariable Integer id) {
        roomBookService.deleteRoomBook(id);
    }

    //업데이트
    @PutMapping("/update/{id}")
    public void updateRoomBook(@PathVariable Integer id, @RequestBody RoomBookDto dto) {
        roomBookService.updateRoomBook(id, dto);
    }
}
