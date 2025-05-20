package com.gnomeshift.mfixflow.statusLogger;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusLogRepository extends JpaRepository<StatusLog, Long> {
    List<StatusLog> findByFixRequestId(long id);
}
