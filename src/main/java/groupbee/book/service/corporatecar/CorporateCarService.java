package groupbee.book.service.corporatecar;

import groupbee.book.entity.CorporateCarEntity;
import groupbee.book.repository.corporatecar.CorporateCarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CorporateCarService {
    private final CorporateCarRepository corporateCarRepository;

    public List<CorporateCarEntity> getAllCorporateCars() {
        return corporateCarRepository.findAll();
    }

    public CorporateCarEntity getCorporateCar(Long id) {
        return corporateCarRepository.findById(id).orElse(null);
    }
}