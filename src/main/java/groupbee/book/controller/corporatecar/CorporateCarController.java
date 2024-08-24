package groupbee.book.controller.corporatecar;

import groupbee.book.entity.CorporateCarEntity;
import groupbee.book.service.corporatecar.CorporateCarService;
import groupbee.book.service.minio.MinioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cars")
public class CorporateCarController {

    private final CorporateCarService corporateCarService;
    private final MinioService minioService;

    @GetMapping("/list")
    public List<CorporateCarEntity> listCorporateCars() {
        return corporateCarService.getAllCorporateCars();
    }

    @PostMapping("/uploadfile")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = minioService.uploadFile("groupbee", "book", file);
        return fileName;
    }

    // 차량 정보 확인
    @GetMapping("{id}")
    public CorporateCarEntity getCorporateCar(@PathVariable Long id) {
        return corporateCarService.getCorporateCar(id);
    }
}