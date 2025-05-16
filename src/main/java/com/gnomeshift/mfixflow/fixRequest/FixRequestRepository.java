package com.gnomeshift.mfixflow.fixRequest;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FixRequestRepository extends JpaRepository<FixRequest, Long> {
    List<FixRequest> findAllByDeviceId(long id);
    List<FixRequest> findAllByMasterId(long id);
    List<FixRequest> findAllByDefectId(long id);
}
