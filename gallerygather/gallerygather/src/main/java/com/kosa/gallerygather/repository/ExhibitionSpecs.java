package com.kosa.gallerygather.repository;

import com.kosa.gallerygather.entity.Exhibition;
import org.springframework.data.jpa.domain.Specification;

public class ExhibitionSpecs {

    public static Specification<Exhibition> containsTitle(String title) {
        return (root, query, criteriaBuilder) -> {
            if (title == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("title"), "%" + title + "%");
        };
    }
}
