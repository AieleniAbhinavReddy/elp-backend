package com.root.repositories;

import com.root.beans.LessonProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonProgressRepository extends JpaRepository<LessonProgress, Long> {

    List<LessonProgress> findByRegistrationId(Long registrationId);

    Optional<LessonProgress> findByRegistrationIdAndLessonId(Long registrationId, Long lessonId);

    int countByRegistrationId(Long registrationId);

    void deleteByRegistrationId(Long registrationId);
}
