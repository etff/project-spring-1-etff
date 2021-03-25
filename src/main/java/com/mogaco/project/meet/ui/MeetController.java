package com.mogaco.project.meet.ui;

import com.mogaco.project.meet.application.MeetService;
import com.mogaco.project.meet.dto.MainResponseDto;
import com.mogaco.project.meet.dto.MeetRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

/**
 * 모임의 사용자 요청을 처리한다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/meets")
public class MeetController {
    private final MeetService meetService;

    @PostMapping
    public ResponseEntity createMeeting(@RequestBody @Valid MeetRequestDto dto) {
        final Long meetId = meetService.createMeeting(dto);

        return ResponseEntity.created(URI.create("/meets/" + meetId))
                .body(meetId);
    }

    @GetMapping
    public Page<MainResponseDto> getMeetingsByPageRequest(final Pageable pageable) {
        return meetService.getMeetingsByPageRequest(pageable);
    }
}
