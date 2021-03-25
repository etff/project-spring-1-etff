package com.mogaco.project.meet.application;

import com.mogaco.project.meet.domain.Location;
import com.mogaco.project.meet.domain.Meet;
import com.mogaco.project.meet.domain.MeetSupplier;
import com.mogaco.project.meet.domain.MeetTime;
import com.mogaco.project.meet.domain.Message;
import com.mogaco.project.meet.dto.MainResponseDto;
import com.mogaco.project.meet.dto.MeetRequestDto;
import com.mogaco.project.meet.infra.MeetRepository;
import com.mogaco.project.study.domain.Study;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 모임 정보를 다룬다.
 */
@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MeetService {
    private final MeetRepository meetRepository;
    private final LocationConverter locationConverter;

    /**
     * 모임을 생성한다.
     *
     * @param dto 모입 생성 명세서
     * @return 등록된 모임 식별자
     */
    @Transactional
    public Long createMeeting(MeetRequestDto dto) {
        if (Objects.isNull(dto)) {
            throw new IllegalArgumentException();
        }
        final Location location = locationConverter.getLocation(dto);
        final Study study = Study.createStudy(dto.getSubject());

        final Meet meet = Meet.of(getMeetTime(dto), dto.getCount(), location, getMessage(dto), study);
        final Meet saved = meetRepository.save(meet);
        return saved.getId();
    }

    public Page<MainResponseDto> getMeetingsByPageRequest(Pageable pageable) {
        return meetRepository.searchMainPage(pageable);
    }

    private Message getMessage(MeetSupplier meetSupplier) {
        return new Message(meetSupplier.getTitle(), meetSupplier.getMessage());
    }

    private MeetTime getMeetTime(MeetSupplier meetSupplier) {
        return new MeetTime(meetSupplier.getStartedAt(), meetSupplier.getTime());
    }
}
