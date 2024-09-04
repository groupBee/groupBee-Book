package groupbee.book.controller.corporatecar;

import groupbee.book.entity.CorporateCarBookEntity;
import groupbee.book.service.corporatecar.CorporateCarBookService;
import groupbee.book.service.feign.FeignClient;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cars")
public class CorporateCarBookController {
    private final CorporateCarBookService corporateCarBookService;

    @Operation(
            summary = "차량 예약 추가",
            description = "멤버 아이디 별 차량 예약 데이터 추가"
    )
    @PostMapping("/insert")
    public ResponseEntity<CorporateCarBookEntity> insertCorporateCars(@RequestBody CorporateCarBookEntity corporateCarBookEntity) {
        return corporateCarBookService.insertCorporateCars(corporateCarBookEntity);
    }

    @Operation(
            summary = "모든 차량 예약 정보",
            description = "모든 차량 예약 정보 가져오기"
    )
    @GetMapping("/booklist")
    public ResponseEntity<List<CorporateCarBookEntity>> listCorporateCarBook() {
        return corporateCarBookService.getAllCorporateCarBook();
    }

    @Operation(
            summary = "차량 예약 삭제",
            description = "차량 예약 삭제"
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCorporateCarBook(@PathVariable Long id) {
        boolean result = corporateCarBookService.deleteById(id);
        if (result) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "차량 예약 변경",
            description = "차량 예약 변경"
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<CorporateCarBookEntity> updateCorporateCarBook(@PathVariable Long id, @RequestBody CorporateCarBookEntity corporateCarBookEntity) {
        return corporateCarBookService.updateCorporateCarBook(id, corporateCarBookEntity);
    }

    @Operation(
            summary = "차량 예약 리스트",
            description = "멤버 아이디 별 차량 예약 데이터 출력"
    )
    @GetMapping("/list/memberId")
    public ResponseEntity<List<CorporateCarBookEntity>> findByMemberId() {
        return corporateCarBookService.findByMemberId();
    }
}