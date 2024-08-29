package groupbee.book.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import groupbee.book.dto.ReservationDto;
import groupbee.book.entity.CorporateCarBookEntity;
import groupbee.book.entity.CorporateCarEntity;
import groupbee.book.entity.RoomBookEntity;
import groupbee.book.entity.RoomEntity;
import groupbee.book.service.admin.AdminBookService;
import groupbee.book.service.admin.AdminListService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/book")
public class AdminController {
    private final AdminBookService adminBookService;
    private final AdminListService adminListService;

    @Operation(
            summary = "예약 정보 리스트",
            description = "관리자 페이지에서 사용할 모든 예약 정보 리스트"
    )
    @GetMapping("/list")
    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        return adminBookService.getAllReservations();
    }

    @Operation(
            summary = "차량 예약 정보 리스트",
            description = "관리자 페이지에서 사용할 차량 예약 정보 리스트"
    )
    @GetMapping("/book/car")
    public ResponseEntity<List<CorporateCarBookEntity>> getAllCarReservations() {
        return adminBookService.getAllCarReservations();
    }

    @Operation(
            summary = "회의실 예약 정보 리스트",
            description = "관리자 페이지에서 사용할 회의실 예약 정보 리스트"
    )
    @GetMapping("/book/room")
    public ResponseEntity<List<RoomBookEntity>> getAllRoomReservations() {
        return adminBookService.getAllRoomReservations();
    }

    @Operation(
            summary = "등록된 차량 리스트",
            description = "관리자 페이지에서 사용할 등록된 차량 리스트"
    )
    @GetMapping("/list/car")
    public ResponseEntity<List<CorporateCarEntity>> getAllCars() {
        return adminListService.getAllCarList();
    }

    @Operation(
            summary = "등록된 회의실 리스트",
            description = "관리자 페이지에서 사용할 등록된 회의실 리스트"
    )
    @GetMapping("/list/room")
    public ResponseEntity<List<RoomEntity>> getAllRooms() {
        return adminListService.getAllRoomList();
    }

    @Operation(
            summary = "차량 정보 수정",
            description = "관리자 페이지에서 차량 수정하기"
    )
    @PatchMapping("/update/car")
    public ResponseEntity<CorporateCarEntity> updateCar(@ModelAttribute CorporateCarEntity corporateCarEntity,
                                                        @RequestParam(value = "file", required = false) MultipartFile file) {
        return adminListService.updateCar(corporateCarEntity, file);
    }

    @Operation(
            summary = "회의실 정보 변경",
            description = "관리자 페이지에서 회의실 변경하기"
    )
    @PatchMapping("/update/room")
    public ResponseEntity<RoomEntity> updateRoom(@ModelAttribute RoomEntity roomEntity,
                                                  @RequestParam(value = "file", required = false) MultipartFile file) {
        return adminListService.updateRoom(roomEntity, file);
    }

    @Operation(
            summary = "차량, 회의실 데이터 추가",
            description = "관리자 페이지에서 차량, 회의실 데이터 추가하기"
    )
    @PostMapping("/insert")
    public ResponseEntity<?> insertData(
            @RequestParam("entityData") String entityDataJson,  // JSON 데이터를 String 으로 받음
            @RequestParam("file") MultipartFile file,
            @RequestParam("category") Long category) {

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> entityData;

        try {
            entityData = objectMapper.readValue(entityDataJson, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body("Invalid JSON data");
        }

        return adminListService.insertData(entityData, file, category);
    }

    @Operation(
            summary = "차량, 회의실 데이터 삭제",
            description = "관리자 페이지에서 차량, 회의실 데이터 삭제하기"
    )
    @DeleteMapping("/book/delete")
    public ResponseEntity<?> deleteData(@RequestParam("id") Long id, @RequestParam("category") Long category) {
        boolean result = adminListService.deleteData(id, category);
        if (result) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
