package com.mogaco.project.study.domain;

import java.util.List;

public interface StudyRepository {
    List<Study> findByMemberId(Long id);
}
