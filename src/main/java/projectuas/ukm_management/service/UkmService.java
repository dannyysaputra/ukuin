package projectuas.ukm_management.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import projectuas.ukm_management.data.entity.Ukm;

public interface UkmService {
    List<Ukm> getAllUkms();

    Ukm getUkmById(Long ukmId);

    Ukm pushUkm(Ukm newUkm);

    Ukm updateUkm(Ukm updatedUkm, Long ukmId);

    void deleteUkm(Long ukmId);

    List<Ukm> getByUkmName(String keyword);

    List<Ukm> getByUkmNameAsc();
    List<Ukm> getByUkmNameDesc();
}
