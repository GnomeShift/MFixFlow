package com.gnomeshift.mfixflow.device;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DeviceDTO {
    private long id;

    @NotBlank
    @Size(max = 100)
    private String name;

    private String description;
}
