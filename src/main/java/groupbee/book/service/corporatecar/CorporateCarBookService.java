package groupbee.book.service.corporatecar;

import groupbee.book.data.corporatecar.CorporateCarBookDto;
import groupbee.book.repository.corporatecar.CorporateCarBookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CorporateCarBookService {

    private CorporateCarBookRepository corporateCarBookRepository;

    public void insertCorporateCars(CorporateCarBookDto dto)
    {
        corporateCarBookRepository.save(dto);
    }

    public List<CorporateCarBookDto> getAllCorporateCarBook() {
        return corporateCarBookRepository.findAll();
    }

    // 예약 삭제
    public void deleteCorporateCarBook(Integer id) {
        corporateCarBookRepository.deleteById(id);
    }

    //업데이트
    public void updateCorporateCarBook(Integer id, CorporateCarBookDto dto) {
        // 예약이 존재하는지 확인
        if (corporateCarBookRepository.existsById(id)) {
            // 예약의 ID를 설정하고 저장
            dto.setId(id);
            corporateCarBookRepository.save(dto);
        } else {
            // 예약이 존재하지 않으면 예외 발생
            throw new RuntimeException("Booking not found with id: " + id);
        }
    }


}