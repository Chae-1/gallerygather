package com.kosa.gallerygather.repository;

import com.kosa.gallerygather.entity.Exhibition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ExhibitionRepository extends JpaRepository<Exhibition, Long>, JpaSpecificationExecutor<Exhibition> {

    @Query("select e from Exhibition e")
    Page<Exhibition> fetchExhibitionsWithPagination(Pageable pageable);

}
