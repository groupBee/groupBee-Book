package groupbee.book.controller.room;

import groupbee.book.entity.RoomBookEntity;
import groupbee.book.service.room.RoomBookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
public class RoomBookController {
    private final RoomBookService roomBookService;

    @Operation(
            summary = "회의실 예약 추가",
            description = "멤버 아이디 별 회의실 예약 데이터 추가"
    )
    @PostMapping("/insert")
    public ResponseEntity<RoomBookEntity> insertRooms(@RequestBody RoomBookEntity roomBookEntity) {
        return roomBookService.insertRooms(roomBookEntity);
    }

    @Operation(
            summary = "모든 회의실 예약 정보",
            description = "모든 회의실 예약 정보 가져오기"
    )
    @GetMapping("/booklist")
    public ResponseEntity<List<RoomBookEntity>> listRoomBook() {
        return roomBookService.getAllRoomBook();
    }

    @Operation(
            summary = "회의실 예약 삭제",
            description = "회의실 예약 삭제"
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRoomBook(@PathVariable Long id) {
        boolean result = roomBookService.deleteRoomBook(id);
        if (result) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "회의실 예약 변경",
            description = "회의실 예약 변경"
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<RoomBookEntity> updateRoomBook(@PathVariable Long id, @RequestBody RoomBookEntity roomBookEntity) {
        return roomBookService.updateRoomBook(id, roomBookEntity);
    }
}
