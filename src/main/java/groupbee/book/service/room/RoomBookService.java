package groupbee.book.service.room;

import feign.FeignException;
import groupbee.book.dto.RoomBookDto;
import groupbee.book.entity.RoomBookEntity;
import groupbee.book.pubsub.RedisPublisher;
import groupbee.book.repository.room.RoomBookRepository;
import groupbee.book.service.feign.FeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomBookService {
    private final RoomBookRepository roomBookRepository;
    private final FeignClient feignClient;
    private final RedisPublisher redisPublisher;

    @Transactional
    public ResponseEntity<RoomBookEntity> insertRooms(RoomBookEntity roomBookEntity) {
        try {
            // 1. Feign 클라이언트를 통해 사용자 정보 가져오기
            Map<String, Object> response = feignClient.getEmployeeInfo();
            roomBookEntity.setMemberId((String) response.get("potalId"));
            roomBookEntity.setEventType("insert");

            // 2. 예약 중복 체크
            boolean exists = roomBookRepository.existsByRoomIdAnd(
                    roomBookEntity.getRoomId(),
                    roomBookEntity.getEnter(),
                    roomBookEntity.getLeave()
            );

            if (exists) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }

            // 3. 예약 저장
            RoomBookEntity saveEntity = roomBookRepository.save(roomBookEntity);

            // 4. Redis Pub 을 통해 예약 정보 발행
            RoomBookDto roomBookDto = RoomBookDto.fromEntity(saveEntity);
            redisPublisher.publishToRoomBook(roomBookDto);

            // 5. 예약 성공 응답 반환
            return ResponseEntity.status(HttpStatus.CREATED).body(saveEntity);
        } catch (FeignException.BadRequest e) {
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

    public ResponseEntity<List<RoomBookEntity>> getAllRoomBook() {
        try {
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

    // 예약 삭제
    public boolean deleteRoomBook(Long id) {
        if (roomBookRepository.existsById(id)) {
            roomBookRepository.deleteById(id);

            RoomBookDto roomBookDto = new RoomBookDto();
            roomBookDto.setId(id);
            roomBookDto.setEventType("delete");
            redisPublisher.publishToRoomBook(roomBookDto);

            return true;
        } else {
            return false;
        }
    }

    //업데이트
    public ResponseEntity<RoomBookEntity> updateRoomBook(Long id, RoomBookEntity roomBookEntity) {
        try {
            roomBookEntity.setEventType("update");
            roomBookEntity.setId(id);
            RoomBookEntity updateEntity = roomBookRepository.save(roomBookEntity);

            RoomBookDto roomBookDto = RoomBookDto.fromEntity(updateEntity);
            redisPublisher.publishToRoomBook(roomBookDto);

            return ResponseEntity.status(HttpStatus.CREATED).body(updateEntity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<List<RoomBookEntity>> findByMemberId() {
        try {
            Map<String, Object> response = feignClient.getEmployeeInfo();
            String memberId = (String) response.get("potalId");
            return ResponseEntity.ok(roomBookRepository.findByMemberId(memberId));
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
