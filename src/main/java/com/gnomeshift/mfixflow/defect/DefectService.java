package com.gnomeshift.mfixflow.defect;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefectService {
    @Autowired
    private DefectRepository defectRepository;

    private DefectDTO convertToDTO(Defect defect) {
        DefectDTO dto = new DefectDTO();

        dto.setId(defect.getId());
        dto.setDescription(defect.getDescription());
        dto.setName(defect.getName());

        return dto;
    }

    private DefectDTO fillDefectDTO(Defect defect, DefectDTO defectDTO) {
        defect.setDescription(defectDTO.getDescription());
        defect.setName(defectDTO.getName());

        return convertToDTO(defectRepository.save(defect));
    }

    public List<DefectDTO> getAllDefects() {
        return defectRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public DefectDTO getDefectById(long id) {
        return defectRepository.findById(id).map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("Defect not found"));
    }

    public DefectDTO addDefect(DefectDTO defectDTO) {
        Defect defect = new Defect();
        return fillDefectDTO(defect, defectDTO);
    }

    public DefectDTO updateDefect(long id, DefectDTO defectDTO) {
        Defect defect = defectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Defect not found"));
        return fillDefectDTO(defect, defectDTO);
    }

    public void deleteDefect(long id) {
        if (!defectRepository.existsById(id)) {
            throw new EntityNotFoundException("Defect not found");
        }

        defectRepository.deleteById(id);
    }
}
