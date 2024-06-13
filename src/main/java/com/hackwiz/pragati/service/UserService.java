package com.hackwiz.pragati.service;

import com.hackwiz.pragati.dao.redis.JobDetailsEntity;
import com.hackwiz.pragati.dao.redis.ProfessionalDetailJobDetailMapEntity;
import com.hackwiz.pragati.dao.redis.ProfessionalDetails;
import com.hackwiz.pragati.dao.redis.RecruiterDetailsEntity;
import com.hackwiz.pragati.dao.redis.UserDetailsEntity;
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

    public long loginRegisterUser(LoginRegisterRequest loginRegisterRequest) {
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


    public GetProfessionalDetailsResponse getProfessionalUserDetails(long userId) {
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
        getProfessionalDetailsResponse.setUserType(userDetailsEntity.getUserType());
        getProfessionalDetailsResponse.setAddress(professionalDetails != null ? professionalDetails.getAddress() : null);
        getProfessionalDetailsResponse.setKycVerified(professionalDetails != null && professionalDetails.isKycVerified());
        getProfessionalDetailsResponse.setSkills(getSkill(professionalDetails));
        getProfessionalDetailsResponse.setJobDetails(professionalDetails != null ? getProfessionalUserJobDetails(professionalDetails) : Collections.emptyList());

        return getProfessionalDetailsResponse;
    }


    public static List<GetProfessionalDetailsResponse.SkillResponse> getSkill(ProfessionalDetails professionalDetails) {
        List<GetProfessionalDetailsResponse.SkillResponse> skillResponses = Collections.emptyList();
        if (professionalDetails != null && professionalDetails.getSkills() != null) {
            skillResponses = professionalDetails.getSkills().stream().map(skill -> {
                GetProfessionalDetailsResponse.SkillResponse skillResponse = new GetProfessionalDetailsResponse.SkillResponse();
                skillResponse.setSkill(skill.getName());
                skillResponse.setRate(skill.getRate());
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

    public GetRecruiterDetailsResponse getRecruiterDetails(long userId) {
        log.info("LoginRegisterService.getRecruiterUserDetails: userId={}", userId);
        Optional<UserDetailsEntity> userDetails = userDetailsRepo.findById(userId);
        UserDetailsEntity userDetailsEntity = userDetails.orElse(null);
        if (userDetailsEntity == null ){
            log.error("LoginRegisterService.getRecruiterUserDetails: User not found with userId={}", userId);
            throw new RuntimeException("User not found with userId=" + userId);
        }

        RecruiterDetailsEntity recruiterDetailsEntity = recruiterDetailsRepo.findByUserId(userDetailsEntity.getId());
        GetRecruiterDetailsResponse getRecruiterDetailsResponse = new GetRecruiterDetailsResponse();
        getRecruiterDetailsResponse.setUserId(userDetailsEntity.getId());
        getRecruiterDetailsResponse.setUserType(userDetailsEntity.getUserType());
//        getRecruiterDetailsResponse.setAddress(professionalDetails != null ? professionalDetails.getAddress() : null);
//        getRecruiterDetailsResponse.setKycVerified(professionalDetails != null && professionalDetails.isKycVerified());
        getRecruiterDetailsResponse.setJobDetails(recruiterDetailsEntity != null ? getRecruiterJobDetails(recruiterDetailsEntity.getId()) : Collections.emptyList());
        return getRecruiterDetailsResponse;
    }


    public List<JobDetailsEntity> getRecruiterJobDetails(Long recruiterDetailId) {
        return jobDetailsRepo.findAllByRecruiterDetailsId(recruiterDetailId);
    }

}
