package com.gnomeshift.mfixflow.fixRequest;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class FixRequestDTO {
    private long id;

    @Size(max = 100)
    private String title;

    private String description;
    private FixRequestStatus status;
    private long masterId;
    private long deviceId;
    private long defectId;
}
