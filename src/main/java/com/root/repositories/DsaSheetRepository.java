package com.root.repositories;

import com.root.beans.DsaSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DsaSheetRepository extends JpaRepository<DsaSheet, Long> {
}
