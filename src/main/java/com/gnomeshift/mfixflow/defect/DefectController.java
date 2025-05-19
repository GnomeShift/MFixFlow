package com.gnomeshift.mfixflow.defect;

import org.springframework.beans.factory.annotation.Autowired;
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
    public DefectDTO addDefect(@RequestBody DefectDTO defectDTO) {
        return defectService.addDefect(defectDTO);
    }

    @PutMapping("/{id}")
    public DefectDTO updateDefect(@PathVariable long id, @RequestBody DefectDTO defectDTO) {
        return defectService.updateDefect(id, defectDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteDefect(@PathVariable long id) {
        defectService.deleteDefect(id);
    }
}
