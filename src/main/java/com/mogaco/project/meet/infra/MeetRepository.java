package com.mogaco.project.meet.infra;

import com.mogaco.project.meet.domain.Meet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetRepository extends JpaRepository<Meet, Long> {
}
