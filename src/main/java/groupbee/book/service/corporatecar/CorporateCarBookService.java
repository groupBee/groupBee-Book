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
public class CorporateCarBookService {
    private final CorporateCarBookRepository corporateCarBookRepository;
    private final FeignClient feignClient;
    private final RedisPublisher redisPublisher;

    @Transactional
    public ResponseEntity<CorporateCarBookEntity> insertCorporateCars(CorporateCarBookEntity corporateCarBookEntity) {
        try {
            Map<String, Object> response = feignClient.getEmployeeInfo();
            corporateCarBookEntity.setMemberId((String) response.get("potalId"));
            corporateCarBookEntity.setEventType("insert");

            boolean exists = corporateCarBookRepository.existsByCorporateCarIdAnd(
                    corporateCarBookEntity.getCorporateCarId(),
                    corporateCarBookEntity.getRentDay(),
                    corporateCarBookEntity.getReturnDay()
            );

            if (exists) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }

            CorporateCarBookEntity saveEntity = corporateCarBookRepository.save(corporateCarBookEntity);

            CarBookDto carBookDto = CarBookDto.fromEntity(saveEntity);
            redisPublisher.publishToCarBook(carBookDto);

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

            CarBookDto carBookDto = new CarBookDto();
            carBookDto.setId(id);
            carBookDto.setEventType("delete");
            redisPublisher.publishToCarBook(carBookDto);

            return true;
        } else {
            return false;
        }
    }

    //업데이트
    public ResponseEntity<CorporateCarBookEntity> updateCorporateCarBook(Long id, CorporateCarBookEntity corporateCarBookEntity) {
        try {
            corporateCarBookEntity.setEventType("update");
            corporateCarBookEntity.setId(id);
            CorporateCarBookEntity updateEntity = corporateCarBookRepository.save(corporateCarBookEntity);

            CarBookDto carBookDto = CarBookDto.fromEntity(updateEntity);
            redisPublisher.publishToCarBook(carBookDto);

            return ResponseEntity.status(HttpStatus.CREATED).body(updateEntity);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<List<CorporateCarBookEntity>> findByMemberId(String memberId) {
        try {
            return ResponseEntity.ok(corporateCarBookRepository.findByMemberId(memberId));
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