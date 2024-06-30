package projectuas.ukm_management.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import projectuas.ukm_management.data.entity.Ukm;

import java.util.List;

public interface UkmRepository extends JpaRepository<Ukm, Long> {
    // custom query
//    @Query(value = "select * from Ukm where Ukm.Ukm_name like %:keyword%", nativeQuery = true)
//    List<Ukm> findByUkmName(@Param("keyword") String keyword);

    List<Ukm> findByName(String name);
    List<Ukm> findAllByOrderByNameAsc();
    List<Ukm> findAllByOrderByNameDesc();
}
