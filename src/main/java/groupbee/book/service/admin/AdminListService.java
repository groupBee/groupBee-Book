package groupbee.book.service.admin;

import feign.FeignException;
import groupbee.book.entity.CorporateCarEntity;
import groupbee.book.entity.RoomEntity;
import groupbee.book.repository.corporatecar.CorporateCarRepository;
import groupbee.book.repository.room.RoomRepository;
import groupbee.book.service.minio.MinioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminListService {
    private final CorporateCarRepository corporateCarRepository;
    private final RoomRepository roomRepository;
    private final MinioService minioService;

    public ResponseEntity<List<CorporateCarEntity>> getAllCarList() {
        try {
            return ResponseEntity.ok(corporateCarRepository.findAll());
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

    public ResponseEntity<List<RoomEntity>> getAllRoomList() {
        try {
            return ResponseEntity.ok(roomRepository.findAll());
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

    public ResponseEntity<CorporateCarEntity> updateCar(CorporateCarEntity corporateCarEntity, MultipartFile file) {
        // 기존 차량 정보를 DB에서 가져옴
        Optional<CorporateCarEntity> existingCarOptional = corporateCarRepository.findById(corporateCarEntity.getId());
        if (existingCarOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        CorporateCarEntity existingCar = existingCarOptional.get();

        if (corporateCarEntity.getType() != null) {
            existingCar.setType(corporateCarEntity.getType());
        }
        if (corporateCarEntity.getCarId() != null) {
            existingCar.setCarId(corporateCarEntity.getCarId());
        }
        if (file != null && !file.isEmpty()) {
            try {
                String originalFileName = existingCar.getPhoto();
                minioService.deleteFile("groupbee", "book", originalFileName);
                String fileName = minioService.uploadFile("groupbee", "book", file);
                existingCar.setPhoto(fileName);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        // 업데이트된 엔티티를 저장
        CorporateCarEntity updatedCar = corporateCarRepository.save(existingCar);
        return ResponseEntity.ok(updatedCar);
    }

    public ResponseEntity<RoomEntity> updateRoom(RoomEntity roomEntity, MultipartFile file) {
        Optional<RoomEntity> existingRoomOptional = roomRepository.findById(roomEntity.getId());
        if (existingRoomOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        RoomEntity existingRoom = existingRoomOptional.get();
        if (roomEntity.getName() != null) {
            existingRoom.setName(roomEntity.getName());
        }
        if (file != null && !file.isEmpty()) {
            try {
                String originalFileName = existingRoom.getPhoto();
                minioService.deleteFile("groupbee", "book", originalFileName);
                String fileName = minioService.uploadFile("groupbee", "book", file);
                existingRoom.setPhoto(fileName);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        RoomEntity updatedRoom = roomRepository.save(existingRoom);
        return ResponseEntity.ok(updatedRoom);
    }

    public ResponseEntity<?> insertData(Map<String, Object> entityData, MultipartFile file, Long category) {
        Object entity;

        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is required");
        }

        String fileName;
        try {
            fileName = minioService.uploadFile("groupbee", "book", file);
        } catch (FeignException e) {
            System.out.println("Feign Exception during file upload: " + e.getMessage());
            return ResponseEntity.status(e.status()).body("File upload failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception during file upload: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error during file upload");
        }

        if (category == 0) {
            entity = CorporateCarEntity.builder()
                    .carId((String) entityData.get("carId"))
                    .type((String) entityData.get("type"))
                    .photo(fileName)
                    .build();
        } else if (category == 1) {
            entity = RoomEntity.builder()
                    .name((String) entityData.get("name"))
                    .photo(fileName)
                    .build();
        } else {
            return ResponseEntity.badRequest().body("Invalid category");
        }

        try {
            if (entity instanceof CorporateCarEntity) {
                CorporateCarEntity saveEntity = corporateCarRepository.save((CorporateCarEntity) entity);
                return ResponseEntity.ok(saveEntity);
            } else if (entity != null) {
                RoomEntity saveEntity = roomRepository.save((RoomEntity) entity);
                return ResponseEntity.ok(saveEntity);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected entity type");
            }
        } catch (FeignException e) {
            System.out.println("Feign Exception during entity save: " + e.getMessage());
            return ResponseEntity.status(e.status()).body("Entity save failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception during entity save: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error during entity save");
        }
    }

    public boolean deleteData(Long id, Long category) {
        if (category == 0) {
            // 차량 데이터 삭제
            if (corporateCarRepository.existsById(id)) {
                String fileName = corporateCarRepository.findById(id).get().getPhoto();
                minioService.deleteFile("groupbee", "book", fileName);
                corporateCarRepository.deleteById(id);
                return true;
            }
        } else if (category == 1) {
            // 회의실 데이터 삭제
            if (roomRepository.existsById(id)) {
                String fileName = roomRepository.findById(id).get().getPhoto();
                minioService.deleteFile("groupbee", "book", fileName);
                roomRepository.deleteById(id);
                return true;
            }
        }
        return false;
    }
}
