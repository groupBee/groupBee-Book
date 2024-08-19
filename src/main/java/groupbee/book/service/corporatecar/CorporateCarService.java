package groupbee.book.service.corporatecar;

import groupbee.book.data.corporatecar.CorporateCarDto;
import groupbee.book.repository.corporatecar.CorporateCarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CorporateCarService {

    @Autowired
    private CorporateCarRepository corporateCarRepository;

    public List<CorporateCarDto> getAllCorporateCars() {
        return corporateCarRepository.findAll();
    }
}