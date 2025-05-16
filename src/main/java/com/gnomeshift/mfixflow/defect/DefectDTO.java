package com.gnomeshift.mfixflow.defect;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DefectDTO {
    private long id;

    @NotBlank
    @Size(max = 100)
    private String name;

    private String description;
}
