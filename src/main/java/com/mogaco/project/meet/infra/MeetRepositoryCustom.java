package com.mogaco.project.meet.infra;

import com.mogaco.project.meet.dto.MainResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MeetRepositoryCustom {
    Page<MainResponseDto> searchMainPage(Pageable pageable);
}
