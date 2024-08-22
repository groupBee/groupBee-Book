package groupbee.book.service.corporatecar;

import feign.Feign;
import feign.FeignException;
import groupbee.book.dto.CarBookDto;
import groupbee.book.entity.CorporateCarBookEntity;
import groupbee.book.pubsub.RedisPublisher;
import groupbee.book.repository.corporatecar.CorporateCarBookRepository;
import groupbee.book.service.feign.FeignClient;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CorporateCarBookService {
    private final CorporateCarBookRepository corporateCarBookRepository;
    private final FeignClient feignClient;
    private final RedisPublisher redisPublisher;

    public ResponseEntity<CorporateCarBookEntity> insertCorporateCars(CorporateCarBookEntity corporateCarBookEntity) {
        try {
            Map<String, Object> response = feignClient.getEmployeeInfo();
            Map<String, Object> data = (Map<String, Object>) response.get("data");
            corporateCarBookEntity.setMemberId((String) data.get("potal_id"));

            CorporateCarBookEntity saveEntity = corporateCarBookRepository.save(corporateCarBookEntity);
            // 예약 정보가 저장된 후, Redis Pub 을 통해 발행
            CarBookDto carBookDto = CarBookDto.fromEntity(saveEntity);
            redisPublisher.publish(carBookDto);

            return ResponseEntity.status(HttpStatus.CREATED).body(saveEntity);
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

    public ResponseEntity<List<CorporateCarBookEntity>> getAllCorporateCarBook() {
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

    // 예약 삭제
    public boolean deleteById(Long id) {
        if (corporateCarBookRepository.existsById(id)) {
            corporateCarBookRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    //업데이트
    public void updateCorporateCarBook(Long id, CorporateCarBookEntity corporateCarBookEntity) {
        // 예약이 존재하는지 확인
        if (corporateCarBookRepository.existsById(id)) {
            // 예약의 ID를 설정하고 저장
            corporateCarBookEntity.setId(id);
            corporateCarBookRepository.save(corporateCarBookEntity);
        } else {
            // 예약이 존재하지 않으면 예외 발생
            throw new RuntimeException("Booking not found with id: " + id);
        }
    }


}