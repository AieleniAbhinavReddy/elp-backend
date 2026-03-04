package com.root.repositories;

import com.root.beans.DsaProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DsaProblemRepository extends JpaRepository<DsaProblem, Long> {

    List<DsaProblem> findByDsaSheetIdOrderByOrderIndexAsc(Long sheetId);

    int countByDsaSheetId(Long sheetId);

    boolean existsByYoutubeVideoIdAndDsaSheetId(String youtubeVideoId, Long sheetId);
}
