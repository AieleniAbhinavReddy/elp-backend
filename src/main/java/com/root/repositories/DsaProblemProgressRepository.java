package com.root.repositories;

import com.root.beans.DsaProblemProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DsaProblemProgressRepository extends JpaRepository<DsaProblemProgress, Long> {

    List<DsaProblemProgress> findByUserId(Long userId);

    Optional<DsaProblemProgress> findByUserIdAndDsaProblemId(Long userId, Long problemId);

    List<DsaProblemProgress> findByUserIdAndDsaProblem_DsaSheetId(Long userId, Long sheetId);

    int countByUserIdAndDsaProblem_DsaSheetId(Long userId, Long sheetId);

    int countByUserId(Long userId);
}
