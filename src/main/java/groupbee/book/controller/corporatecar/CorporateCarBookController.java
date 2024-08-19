package groupbee.book.controller.corporatecar;

import groupbee.book.data.corporatecar.CorporateCarBookDto;
import groupbee.book.service.corporatecar.CorporateCarBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cars")
public class CorporateCarBookController {

    private final CorporateCarBookService corporateCarBookService;

    @PostMapping("/insert")
    public void insertCorporateCars(@RequestBody CorporateCarBookDto dto)
    {
        corporateCarBookService.insertCorporateCars(dto);
    }

    @GetMapping("/booklist")
    public List<CorporateCarBookDto> listCorporateCarBook() {
        return corporateCarBookService.getAllCorporateCarBook();
    }

    // 예약 삭제
    @DeleteMapping("/delete/{id}")
    public void deleteCorporateCarBook(@PathVariable Integer id) {
        corporateCarBookService.deleteCorporateCarBook(id);
    }

    //업데이트
    @PutMapping("/update/{id}")
    public void updateCorporateCarBook(@PathVariable Integer id, @RequestBody CorporateCarBookDto dto) {
        corporateCarBookService.updateCorporateCarBook(id, dto);
    }
}