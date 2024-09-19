package com.tujuhsembilan.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tujuhsembilan.app.dto.response.EmployeeStatusResponse;
import com.tujuhsembilan.app.dto.response.PositionResponse;
import com.tujuhsembilan.app.dto.response.TalentLevelResponse;
import com.tujuhsembilan.app.dto.response.TalentSkillsetResponse;
import com.tujuhsembilan.app.dto.response.TalentStatusResponse;
import com.tujuhsembilan.app.services.MasterTalentService;

@RestController
@RequestMapping("/api/master-managements")
public class MasterTalentController {

    @Autowired
    private MasterTalentService masterTalentService;

    @GetMapping("/talent-position-option-list")
    public List<PositionResponse> getMasterPosisiTalent() {
        return masterTalentService.getMasterPosisiTalent();
    }

    @GetMapping("/talent-level-option-list")
    public List<TalentLevelResponse> getMasterLevelTalent() {
        return masterTalentService.getMasterLevelTalent();
    }

    @GetMapping("/employee-status-option-list")
    public List<EmployeeStatusResponse> getMasterEmployeeStatus() {
        return masterTalentService.getMasterEmployeeStatus();
    }

    @GetMapping("/skill-set-option-list")
    public List<TalentSkillsetResponse> getMasterSkillset() {
        return masterTalentService.getMasterSkillset();
    }

    @GetMapping("/talent-status-option-list")
    public List<TalentStatusResponse> getMasterStatusTalent() {
        return masterTalentService.getMasterStatusTalent();
    }
}
