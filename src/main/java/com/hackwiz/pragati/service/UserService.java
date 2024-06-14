package com.hackwiz.pragati.service;

import com.hackwiz.pragati.dao.redis.JobDetailsEntity;
import com.hackwiz.pragati.dao.redis.ProfessionalDetailJobDetailMapEntity;
import com.hackwiz.pragati.dao.redis.ProfessionalDetails;
import com.hackwiz.pragati.dao.redis.RecruiterDetailsEntity;
import com.hackwiz.pragati.dao.redis.UserDetailsEntity;
import com.hackwiz.pragati.enums.Skill;
import com.hackwiz.pragati.models.requests.AddSkillsKycRequest;
import com.hackwiz.pragati.models.requests.LoginRegisterRequest;
import com.hackwiz.pragati.models.responses.GetProfessionalDetailsResponse;
import com.hackwiz.pragati.models.responses.GetRecruiterDetailsResponse;
import com.hackwiz.pragati.repostitory.redis.JobDetailsRepo;
import com.hackwiz.pragati.repostitory.redis.ProfessionalDetailJobDetailMapRepo;
import com.hackwiz.pragati.repostitory.redis.ProfessionalDetailsRepo;
import com.hackwiz.pragati.repostitory.redis.RecruiterDetailsRepo;
import com.hackwiz.pragati.repostitory.redis.UserDetailsRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    private final UserDetailsRepo userDetailsRepo;
    private final ProfessionalDetailsRepo professionalDetailsRepo;

    private final ProfessionalDetailJobDetailMapRepo professionalDetailJobDetailMapRepo;
    private final JobDetailsRepo jobDetailsRepo;

    private final RecruiterDetailsRepo recruiterDetailsRepo;

    public UserService(UserDetailsRepo userDetailsRepo, ProfessionalDetailsRepo professionalDetailsRepo, ProfessionalDetailJobDetailMapRepo professionalDetailJobDetailMapRepo, JobDetailsRepo jobDetailsRepo
    , RecruiterDetailsRepo recruiterDetailsRepo) {
        this.userDetailsRepo = userDetailsRepo;
        this.professionalDetailsRepo = professionalDetailsRepo;
        this.professionalDetailJobDetailMapRepo = professionalDetailJobDetailMapRepo;
        this.jobDetailsRepo = jobDetailsRepo;
        this.recruiterDetailsRepo = recruiterDetailsRepo;
    }

    public String loginRegisterUser(LoginRegisterRequest loginRegisterRequest) {
        log.info("LoginRegisterService.loginRegisterUser: loginRegisterRequest={}", loginRegisterRequest);

        List<UserDetailsEntity> existingUserList = userDetailsRepo.findByPhone(loginRegisterRequest.getPhone());
        UserDetailsEntity existingUser = existingUserList.isEmpty() ? null : existingUserList.get(0);
        if (existingUser != null) {
            log.info("LoginRegisterService.loginRegisterUser: User found with phone number={}", loginRegisterRequest.getPhone());
            if(!existingUser.getPassword().equals(loginRegisterRequest.getPassword())) {
                log.error("LoginRegisterService.loginRegisterUser: Password mismatch for phone number={}", loginRegisterRequest.getPhone());
                throw new RuntimeException("Password mismatch for user with phone number=" + loginRegisterRequest.getPhone());
            }
            return existingUser.getId();
        } else {
            log.info("LoginRegisterService.loginRegisterUser: User not found with phone number={}, registering user", loginRegisterRequest.getPhone());
            UserDetailsEntity newUser = UserDetailsEntity.builder()
                    .name(loginRegisterRequest.getName())
                    .email(loginRegisterRequest.getEmail())
                    .phone(loginRegisterRequest.getPhone())
                    .userType(loginRegisterRequest.getType())
                    .password(loginRegisterRequest.getPassword())
                    .build();
            UserDetailsEntity savedUser = userDetailsRepo.save(newUser);
            return savedUser.getId();
        }
    }


    public GetProfessionalDetailsResponse getProfessionalUserDetails(String userId) {
        log.info("LoginRegisterService.getProfessionalUserDetails: userId={}", userId);
        Optional<UserDetailsEntity> userDetails = userDetailsRepo.findById(userId);
        UserDetailsEntity userDetailsEntity = userDetails.orElse(null);
        if (userDetailsEntity == null ){
            log.error("LoginRegisterService.getProfessionalUserDetails: User not found with userId={}", userId);
            throw new RuntimeException("User not found with userId=" + userId);
        }

        List<ProfessionalDetails> professionalDetailsList = professionalDetailsRepo.findByUserId(userDetailsEntity.getId());
        ProfessionalDetails professionalDetails = professionalDetailsList.isEmpty() ? null : professionalDetailsList.get(0);
        GetProfessionalDetailsResponse getProfessionalDetailsResponse = new GetProfessionalDetailsResponse();
        getProfessionalDetailsResponse.setUserId(userDetailsEntity.getId());
        getProfessionalDetailsResponse.setName(userDetailsEntity.getName());
        getProfessionalDetailsResponse.setUserType(userDetailsEntity.getUserType());
        getProfessionalDetailsResponse.setAddress(professionalDetails != null ? professionalDetails.getAddress() : null);
        getProfessionalDetailsResponse.setKycVerified(professionalDetails != null && professionalDetails.isKycVerified());
        getProfessionalDetailsResponse.setSkills(getSkill(professionalDetails));
        getProfessionalDetailsResponse.setJobDetails(professionalDetails != null ? getProfessionalUserJobDetails(professionalDetails) : Collections.emptyList());
        getProfessionalDetailsResponse.setAssigned(professionalDetails != null && professionalDetails.isJobAssigned());

        return getProfessionalDetailsResponse;
    }


    public static List<GetProfessionalDetailsResponse.SkillResponse> getSkill(ProfessionalDetails professionalDetails) {
        List<GetProfessionalDetailsResponse.SkillResponse> skillResponses = Collections.emptyList();
        if (professionalDetails != null && professionalDetails.getSkills() != null) {
            skillResponses = professionalDetails.getSkills().stream().map(skill -> {
                GetProfessionalDetailsResponse.SkillResponse skillResponse = new GetProfessionalDetailsResponse.SkillResponse();
                skillResponse.setSkill(skill.name());
                skillResponse.setRate(100f);
                return skillResponse;
            }).toList();
        }
        return skillResponses;
    }

    public List<JobDetailsEntity> getProfessionalUserJobDetails(ProfessionalDetails professionalDetails) {
        List<ProfessionalDetailJobDetailMapEntity> professionalDetailJobDetailMap = professionalDetailJobDetailMapRepo.findByProfessionalDetailId(professionalDetails.getId());
        Iterable<JobDetailsEntity> jobDetails = jobDetailsRepo.findAllById(professionalDetailJobDetailMap.stream().map(ProfessionalDetailJobDetailMapEntity::getJobDetailId).toList());
        return (List<JobDetailsEntity>) jobDetails;
    }

    public GetRecruiterDetailsResponse getRecruiterDetails(String userId) {
        log.info("LoginRegisterService.getRecruiterUserDetails: userId={}", userId);
        Optional<UserDetailsEntity> userDetails = userDetailsRepo.findById(userId);
        UserDetailsEntity userDetailsEntity = userDetails.orElse(null);
        if (userDetailsEntity == null ){
            log.error("LoginRegisterService.getRecruiterUserDetails: User not found with userId={}", userId);
            throw new RuntimeException("User not found with userId=" + userId);
        }

        List<JobDetailsEntity> jobDetailsEntity = jobDetailsRepo.findAllByRecruiterDetailsId(userDetailsEntity.getId());
        GetRecruiterDetailsResponse getRecruiterDetailsResponse = new GetRecruiterDetailsResponse();
        getRecruiterDetailsResponse.setUserId(userDetailsEntity.getId());
        getRecruiterDetailsResponse.setName(userDetailsEntity.getName());
        getRecruiterDetailsResponse.setUserType(userDetailsEntity.getUserType());
//        getRecruiterDetailsResponse.setAddress(professionalDetails != null ? professionalDetails.getAddress() : null);
//        getRecruiterDetailsResponse.setKycVerified(professionalDetails != null && professionalDetails.isKycVerified());
        getRecruiterDetailsResponse.setJobDetails(jobDetailsEntity.stream().map(this::getJobDetailsView).toList());
        return getRecruiterDetailsResponse;
    }


    public List<JobDetailsEntity> getRecruiterJobDetails(String recruiterDetailId) {
        return jobDetailsRepo.findAllByRecruiterDetailsId(recruiterDetailId);
    }

    private GetRecruiterDetailsResponse.JobDetailsView getJobDetailsView(JobDetailsEntity jobDetailsEntity) {
        List<String> professionalDetailIds = professionalDetailJobDetailMapRepo.findByJobDetailId(jobDetailsEntity.getId()).stream().map(ProfessionalDetailJobDetailMapEntity::getProfessionalDetailId).toList();
        Iterable<ProfessionalDetails> professionalDetails = professionalDetailsRepo.findAllById(professionalDetailIds);
        List<ProfessionalDetails> professionalDetailsList = (List<ProfessionalDetails>) professionalDetails;
        List<String> userIds = professionalDetailsList.stream().map(ProfessionalDetails::getUserId).toList();
        Iterable<UserDetailsEntity> userDetails = userDetailsRepo.findAllById(userIds);
        GetRecruiterDetailsResponse.JobDetailsView jobDetailsView = new GetRecruiterDetailsResponse.JobDetailsView();
        jobDetailsView.setUserDetailsEntityList((List<UserDetailsEntity>) userDetails);
        jobDetailsView.setId(jobDetailsEntity.getId());
        jobDetailsView.setJobStatus(jobDetailsEntity.getJobStatus());
        jobDetailsView.setRecruiterDetailsId(jobDetailsEntity.getRecruiterDetailsId());
        jobDetailsView.setActive(jobDetailsEntity.isActive());
        jobDetailsView.setAddress(jobDetailsEntity.getAddress());
        jobDetailsView.setTimeline(jobDetailsEntity.getTimeline());
        jobDetailsView.setRequiredProfessionals(jobDetailsView.getRequiredProfessionals());

        return jobDetailsView;

    }


    public GetProfessionalDetailsResponse addProfessionalSkills(AddSkillsKycRequest addSkillsKycRequest) {
        log.info("LoginRegisterService.addProfessionalSkills: userId={}", addSkillsKycRequest.getUserId());
        Optional<UserDetailsEntity> userDetails = userDetailsRepo.findById(addSkillsKycRequest.getUserId());
        UserDetailsEntity userDetailsEntity = userDetails.orElse(null);
        if (userDetailsEntity == null ){
            log.error("LoginRegisterService.addProfessionalSkills: User not found with userId={}", addSkillsKycRequest.getUserId());
            throw new RuntimeException("User not found with userId=" + addSkillsKycRequest.getUserId());
        }


        List<ProfessionalDetails> professionalDetailsList = professionalDetailsRepo.findByUserId(userDetailsEntity.getId());
        ProfessionalDetails professionalDetails = professionalDetailsList.isEmpty() ? null : professionalDetailsList.get(0);
        if (professionalDetails == null) {
            List<Skill> skillList = new ArrayList<>();
            for(AddSkillsKycRequest.SkillRequest skillRequest : addSkillsKycRequest.getSkillList()) {
                skillList.add(skillRequest.getSkillName());
            }

            ProfessionalDetails professionalDetailsEntity = ProfessionalDetails.builder()
                    .userId(userDetailsEntity.getId())
                    .address(addSkillsKycRequest.getAddress())
                    .kycVerified(addSkillsKycRequest.isMarkKyc())
                    .availability(addSkillsKycRequest.getAvailability())
                    .skills(skillList)
                    .build();

            professionalDetailsRepo.save(professionalDetailsEntity);
        } else {
            List<Skill> skillList = Optional.ofNullable(professionalDetails.getSkills()).orElse(new ArrayList<>());
            for(AddSkillsKycRequest.SkillRequest skillRequest : addSkillsKycRequest.getSkillList()) {
                skillList.add(skillRequest.getSkillName());
            }
            professionalDetails.setSkills(skillList);
            professionalDetails.setAvailability(addSkillsKycRequest.getAvailability() != null ? addSkillsKycRequest.getAvailability() : professionalDetails.getAvailability());
            professionalDetails.setAddress(addSkillsKycRequest.getAddress() != null ? addSkillsKycRequest.getAddress() : professionalDetails.getAddress());

            professionalDetailsRepo.save(professionalDetails);
        }
        return getProfessionalUserDetails(userDetailsEntity.getId());
    }
}
