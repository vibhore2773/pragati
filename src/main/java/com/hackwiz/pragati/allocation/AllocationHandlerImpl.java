package com.hackwiz.pragati.allocation;

import com.hackwiz.pragati.dao.redis.JobDetailsEntity;
import com.hackwiz.pragati.dao.redis.ProfessionalDetailJobDetailMapEntity;
import com.hackwiz.pragati.dao.redis.ProfessionalDetails;
import com.hackwiz.pragati.enums.Skill;
import com.hackwiz.pragati.enums.StaticSkill;
import com.hackwiz.pragati.enums.StaticSkill;
import com.hackwiz.pragati.models.Address;
import com.hackwiz.pragati.models.responses.Timeline;
import com.hackwiz.pragati.repostitory.redis.ProfessionalDetailJobDetailMapRepo;
import com.hackwiz.pragati.repostitory.redis.ProfessionalDetailsRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class AllocationHandlerImpl implements AllocationHandler {

    private final ProfessionalDetailsRepo professionalDetailsRepo;

    private final ProfessionalDetailJobDetailMapRepo professionalDetailJobDetailMapRepo;

    public AllocationHandlerImpl(ProfessionalDetailsRepo userDetailsRepo, ProfessionalDetailJobDetailMapRepo professionalDetailJobDetailMapRepo) {
        this.professionalDetailsRepo = userDetailsRepo;
        this.professionalDetailJobDetailMapRepo = professionalDetailJobDetailMapRepo;
    }

    public boolean processJobAllocation(JobDetailsEntity jobDetailsEntity) {
        List<ProfessionalDetails> professionalDetails = StreamSupport.stream(professionalDetailsRepo.findAll().spliterator(), false).toList();
        List<ProfessionalDetails> matchedProfessionals = getMatchedProfessionals(professionalDetails, jobDetailsEntity);
        if (matchedProfessionals.size() == jobDetailsEntity.getRequiredProfessionals()) {
            List<ProfessionalDetailJobDetailMapEntity> professionalDetailJobDetailMapEntityList = new ArrayList<>();
            for (ProfessionalDetails professionalDetail : matchedProfessionals) {
                professionalDetailJobDetailMapEntityList.add(ProfessionalDetailJobDetailMapEntity.builder()
                                .jobDetailId(jobDetailsEntity.getId())
                                .professionalDetailId(professionalDetail.getId())
                        .build());
                professionalDetail.setJobAssigned(true);
            }
            professionalDetailJobDetailMapRepo.saveAll(professionalDetailJobDetailMapEntityList);
            professionalDetailsRepo.saveAll(matchedProfessionals);
            return true;
        }
        return false;
    }

    private List<ProfessionalDetails> getMatchedProfessionals(List<ProfessionalDetails> professionalDetailsList, JobDetailsEntity jobDetailsEntity) {
        List<ProfessionalDetails> matchedProfessionals = new ArrayList<>();
        for (ProfessionalDetails professionalDetails : professionalDetailsList) {
            if (matchAddress(professionalDetails.getAddress(), jobDetailsEntity.getAddress())
                    && matchSkill(jobDetailsEntity.getSkill(), professionalDetails.getSkills())
                    && matchTimeline(jobDetailsEntity.getTimeline(), professionalDetails.getAvailability())) {
                matchedProfessionals.add(professionalDetails);
            }
        }
        return matchedProfessionals;
    }

    private boolean matchAddress(Address address1, Address address2) {
        return address1.getCity().equals(address2.getCity()) && address1.getPincode().equals(address2.getPincode());
    }

    private boolean matchSkill(Skill requiredSkill, List<Skill> availableSkills) {
        for (Skill staticSkill : availableSkills) {
            if (requiredSkill.equals(staticSkill)) return true;
        }
        return false;
    }

    private boolean matchTimeline(Timeline requiredTimeline, Timeline availableTimeline) {
        return availableTimeline.getStartDate().compareTo(requiredTimeline.getStartDate()) >= 0
                && availableTimeline.getEndDate().compareTo(requiredTimeline.getEndDate()) <= 0;
    }

}
