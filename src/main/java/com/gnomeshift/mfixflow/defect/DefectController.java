package com.gnomeshift.mfixflow.defect;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/defects")
public class DefectController {
    @Autowired
    private DefectService defectService;

    @GetMapping
    public List<DefectDTO> getAllDefects() {
        return defectService.getAllDefects();
    }

    @GetMapping("/{id}")
    public DefectDTO getDefectById(@PathVariable int id) {
        return defectService.getDefectById(id);
    }

    @PostMapping
    public ResponseEntity<DefectDTO> addDefect(@RequestBody DefectDTO defectDTO) {
        try {
            return ResponseEntity.ok(defectService.addDefect(defectDTO));
        }
        catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DefectDTO> updateDefect(@PathVariable long id, @RequestBody DefectDTO defectDTO) {
        try {
            return ResponseEntity.ok(defectService.updateDefect(id, defectDTO));
        }
        catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DefectDTO> deleteDefect(@PathVariable long id) {
        try {
            defectService.deleteDefect(id);
            return ResponseEntity.noContent().build();
        }
        catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
