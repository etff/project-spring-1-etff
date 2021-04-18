package com.mogaco.project.study.infra;

import com.mogaco.project.study.domain.Study;
import com.mogaco.project.study.domain.StudyRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JpaStudyRepository extends CrudRepository<Study, Long>, StudyRepository {
    List<Study> findByMemberId(Long id);
}
