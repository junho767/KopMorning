package com.junho.Kopmorning.Repository;

import com.junho.Kopmorning.DTO.PageResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleRepositoryCustom {
    Page<PageResponseDTO> searchAll(Pageable pageable);
}
