package groupbee.book.service.admin;

import feign.FeignException;
import groupbee.book.dto.ReservationDto;
import groupbee.book.entity.CorporateCarBookEntity;
import groupbee.book.entity.RoomBookEntity;
import groupbee.book.repository.corporatecar.CorporateCarBookRepository;
import groupbee.book.repository.room.RoomBookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminBookService {
    private final CorporateCarBookRepository corporateCarBookRepository;
    private final RoomBookRepository roomBookRepository;

    public ResponseEntity<List<ReservationDto>> getAllReservations() {
        try {
            List<ReservationDto> reservations = new ArrayList<>();

            List<CorporateCarBookEntity> carReservations = corporateCarBookRepository.findAll();
            for (CorporateCarBookEntity carList : carReservations) {
                ReservationDto dto = new ReservationDto();
                dto.setId(carList.getId());
                dto.setType(0L);
                dto.setStartTime(carList.getRentDay());
                dto.setEndTime(carList.getReturnDay());
                dto.setMemberId(carList.getMemberId());
                dto.setReason(carList.getReason());
                reservations.add(dto);
            }

            List<RoomBookEntity> roomReservations = roomBookRepository.findAll();
            for (RoomBookEntity roomList : roomReservations) {
                ReservationDto dto = new ReservationDto();
                dto.setId(roomList.getId());
                dto.setType(1L);
                dto.setStartTime(roomList.getEnter());
                dto.setEndTime(roomList.getLeave());
                dto.setMemberId(roomList.getMemberId());
                dto.setReason(roomList.getPurpose());
                reservations.add(dto);
            }

            return ResponseEntity.ok(reservations);
        } catch (FeignException.BadRequest e) {
            // 400 Bad Request 발생 시 처리
            System.out.println("Bad Request: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (FeignException e) {
            // 기타 FeignException 발생 시 처리
            System.out.println("Feign Exception: " + e.getMessage());
            return ResponseEntity.status(e.status()).build();
        } catch (Exception e) {
            // 일반 예외 처리
            System.out.println("Exception: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<List<CorporateCarBookEntity>> getAllCarReservations() {
        try {
            return ResponseEntity.ok(corporateCarBookRepository.findAll());
        } catch (FeignException.BadRequest e) {
            // 400 Bad Request 발생 시 처리
            System.out.println("Bad Request: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (FeignException e) {
            // 기타 FeignException 발생 시 처리
            System.out.println("Feign Exception: " + e.getMessage());
            return ResponseEntity.status(e.status()).build();
        } catch (Exception e) {
            // 일반 예외 처리
            System.out.println("Exception: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<List<RoomBookEntity>> getAllRoomReservations() {
        try{
            return ResponseEntity.ok(roomBookRepository.findAll());
        } catch (FeignException.BadRequest e) {
            // 400 Bad Request 발생 시 처리
            System.out.println("Bad Request: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (FeignException e) {
            // 기타 FeignException 발생 시 처리
            System.out.println("Feign Exception: " + e.getMessage());
            return ResponseEntity.status(e.status()).build();
        } catch (Exception e) {
            // 일반 예외 처리
            System.out.println("Exception: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
