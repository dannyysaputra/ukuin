package projectuas.ukm_management.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import projectuas.ukm_management.data.entity.Ukm;
import projectuas.ukm_management.data.repository.UkmRepository;
import projectuas.ukm_management.service.UkmService;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class UkmServiceImpl implements UkmService {
    private final Path root = Paths.get("uploads");
    
    private UkmRepository ukmRepository;

    public UkmServiceImpl(UkmRepository ukmRepository) {
        this.ukmRepository = ukmRepository;
    }

    @Override
    public List<Ukm> getAllUkms() {
        return ukmRepository.findAll();
    }

    @Override
    public Ukm getUkmById(Long ukmId) {
        return ukmRepository.findById(ukmId).orElse(null);
    }

    @Override
    public Ukm pushUkm(Ukm newUkm) {
        return ukmRepository.save(newUkm);
    }

    // @Override
    // public List<Ukm> getByUkmName(String movieName) {
    //// return ukmRepository.findByUkmNameContaining(movieName);
    // List<Ukm> ukms = getByUkmNameAsc();
    // int index = binarySearchByUkmName(ukms, movieName);
    //
    // if (index != -1) {
    // List<Ukm> res = new ArrayList<>();
    // res.add(ukms.get(index));
    // return res;
    // } else {
    // return Collections.emptyList();
    // }
    // }

    @Override
    public List<Ukm> getByUkmName(String keyword) {
        List<Ukm> ukms = getByUkmNameAsc();
        List<Ukm> result = new ArrayList<>();

        String[] keywords = keyword.trim().toLowerCase().split("\\s+");

        for (Ukm ukm : ukms) {
            String ukmName = ukm.getName().toLowerCase();

            boolean isMatch = true;
            for (String key : keywords) {
                if (!ukmName.contains(key)) {
                    isMatch = false;
                    break;
                }
            }

            if (isMatch) {
                result.add(ukm);
            }
        }

        return result;
    }

    private int binarySearchByUkmName(List<Ukm> ukms, String keyword) {
        int left = 0;
        int right = ukms.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int res = ukms.get(mid).getName().compareToIgnoreCase(keyword);

            if (res == 0) {
                return mid;
            }

            if (res < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    @Override
    public List<Ukm> getByUkmNameAsc() {
        // return ukmRepository.findAllByOrderByUkmNameAsc();
        List<Ukm> ukms = getAllUkms();
        bubbleSortByNameAsc(ukms);
        return ukms;
    }

    private void bubbleSortByNameAsc(List<Ukm> ukms) {
        int n = ukms.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (ukms.get(j).getName().compareToIgnoreCase(ukms.get(j + 1).getName()) > 0) {
                    Ukm temp = ukms.get(j);
                    ukms.set(j, ukms.get(j + 1));
                    ukms.set(j + 1, temp);
                }
            }
        }
    }

    @Override
    public List<Ukm> getByUkmNameDesc() {
        // return ukmRepository.findAllByOrderByUkmNameDesc();
        List<Ukm> ukms = getAllUkms();
        insertionSortByNameDesc(ukms);
        return ukms;
    }

    private void insertionSortByNameDesc(List<Ukm> ukms) {
        int n = ukms.size();
        for (int i = 1; i < n; ++i) {
            Ukm key = ukms.get(i);
            int j = i - 1;

            while (j >= 0 && ukms.get(j).getName().compareToIgnoreCase(key.getName()) < 0) {
                ukms.set(j + 1, ukms.get(j));
                j = j - 1;
            }
            ukms.set(j + 1, key);
        }
    }

    @Override
    public Ukm updateUkm(Ukm updatedUkm, Long ukmId) {
        return ukmRepository.findById(ukmId).map(ukm -> {
            ukm.setName(updatedUkm.getName());
            ukm.setDescription(updatedUkm.getDescription());
            ukm.setLogo(updatedUkm.getLogo());
            ukm.setEmail(updatedUkm.getEmail());
            ukm.setMission(updatedUkm.getMission());
            ukm.setVision(updatedUkm.getVision());
            return ukmRepository.save(ukm);
        }).orElse(null);
    }

    @Override
    public void deleteUkm(Long ukmId) {
        ukmRepository.deleteById(ukmId);
    }
}
