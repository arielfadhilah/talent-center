package com.tujuhsembilan.app.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tujuhsembilan.app.dto.response.EmployeeStatusResponse;
import com.tujuhsembilan.app.dto.response.PositionResponse;
import com.tujuhsembilan.app.dto.response.TalentLevelResponse;
import com.tujuhsembilan.app.dto.response.TalentSkillsetResponse;
import com.tujuhsembilan.app.dto.response.TalentStatusResponse;
import com.tujuhsembilan.app.model.EmployeeStatus;
import com.tujuhsembilan.app.model.Position;
import com.tujuhsembilan.app.model.Skillset;
import com.tujuhsembilan.app.model.TalentLevel;
import com.tujuhsembilan.app.model.TalentStatus;
import com.tujuhsembilan.app.repository.EmployeeStatusRepository;
import com.tujuhsembilan.app.repository.PositionRepository;
import com.tujuhsembilan.app.repository.SkillsetRepository;
import com.tujuhsembilan.app.repository.TalentLevelRepository;
import com.tujuhsembilan.app.repository.TalentStatusRepository;

@Service
public class MasterTalentService {

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private TalentLevelRepository talentLevelRepository;

    @Autowired
    private EmployeeStatusRepository employeeStatusRepository;

    @Autowired
    private SkillsetRepository skillsetRepository;

    @Autowired
    private TalentStatusRepository talentStatusRepository;

    public List<PositionResponse> getMasterPosisiTalent() {
        List<Position> posisiTalent = positionRepository.findAll();

        return posisiTalent.stream()
                .map(position -> {
                    PositionResponse response = PositionResponse.builder().build();
                    response.setPositionId(position.getPositionId());
                    response.setPositionName(position.getPositionName());
                    return response;
                })
                .collect(Collectors.toList());
    }

    public List<TalentLevelResponse> getMasterLevelTalent() {
        List<TalentLevel> levelTalent = talentLevelRepository.findAll();

        return levelTalent.stream()
            .map(level -> {
                TalentLevelResponse response = TalentLevelResponse.builder().build();
                response.setTalentLevelId(level.getTalentLevelId());
                response.setTalentLevelName(level.getTalentLevelName());
                return response;
            })
            .collect(Collectors.toList());
    }

    public List<EmployeeStatusResponse> getMasterEmployeeStatus() {
        List<EmployeeStatus> employeeStatus = employeeStatusRepository.findAll();

        return employeeStatus.stream()
            .map(employeestat -> {
                EmployeeStatusResponse response = EmployeeStatusResponse.builder().build();
                response.setEmployeeStatusId(employeestat.getEmployeeStatusId());
                response.setEmployeeStatusName(employeestat.getEmployeeStatusName());
                return response;
            })
            .collect(Collectors.toList());
    }

    public List<TalentSkillsetResponse> getMasterSkillset() {
        List<Skillset> skillSet = skillsetRepository.findAll();

        return skillSet.stream()
            .map(skillset -> {
                TalentSkillsetResponse response = TalentSkillsetResponse.builder().build();
                response.setSkillId(skillset.getSkillsetId());
                response.setSkillName(skillset.getSkillsetName());
                return response;
            })
            .collect(Collectors.toList());
    }

    public List<TalentStatusResponse> getMasterStatusTalent() {
        List<TalentStatus> talentStatus = talentStatusRepository.findAll();

        return talentStatus.stream()
            .map(talentstatus -> {
                TalentStatusResponse response = TalentStatusResponse.builder().build();
                response.setTalentStatusId(talentstatus.getTalentStatusId());
                response.setTalentStatusName(talentstatus.getTalentStatusName());
                return response;
            })
            .collect(Collectors.toList());
    }
}
