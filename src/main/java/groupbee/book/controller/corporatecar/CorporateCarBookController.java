package groupbee.book.controller.corporatecar;

import groupbee.book.entity.CorporateCarBookEntity;
import groupbee.book.service.corporatecar.CorporateCarBookService;
import groupbee.book.service.feign.FeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cars")
public class CorporateCarBookController {
    private final CorporateCarBookService corporateCarBookService;

    // 멤버 아이디 별 데이터 추가
    @PostMapping("/insert")
    public ResponseEntity<CorporateCarBookEntity> insertCorporateCars(@RequestBody CorporateCarBookEntity corporateCarBookEntity)
    {
        return corporateCarBookService.insertCorporateCars(corporateCarBookEntity);
    }

    // 전체 리스트 출력
    @GetMapping("/booklist")
    public ResponseEntity<List<CorporateCarBookEntity>> listCorporateCarBook() {
        return corporateCarBookService.getAllCorporateCarBook();
    }

    // 예약 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCorporateCarBook(@PathVariable Long id) {
        boolean result = corporateCarBookService.deleteById(id);
        if (result) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //업데이트
    @PutMapping("/update/{id}")
    public ResponseEntity<CorporateCarBookEntity> updateCorporateCarBook(@PathVariable Long id, @RequestBody CorporateCarBookEntity corporateCarBookEntity) {
        return corporateCarBookService.updateCorporateCarBook(id, corporateCarBookEntity);
    }
}