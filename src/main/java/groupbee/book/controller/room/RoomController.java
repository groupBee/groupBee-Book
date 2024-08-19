package groupbee.book.controller.room;


import groupbee.book.data.room.RoomDto;
import groupbee.book.service.minio.MinioService;
import groupbee.book.service.room.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;
    private final MinioService minioService;

    @GetMapping("/list")
    public List<RoomDto> listRooms() {
        return roomService.getAllRooms();
    }

    @PostMapping("/uploadfile")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = minioService.uploadFile("groupbee", "book", file);
        return fileName;
    }
}
