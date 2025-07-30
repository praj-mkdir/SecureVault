package com.praj.secureVault.controller;

import com.praj.secureVault.dto.UserInitRequestDTO;
import com.praj.secureVault.repository.UserMetaDataRepository;
import com.praj.secureVault.service.UserInitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/internal")
@Slf4j
public class UserInitController {

    private final UserMetaDataRepository userRepo;
    private final UserInitService service;

    public UserInitController(UserMetaDataRepository userRepo, UserInitService service) {
        this.userRepo = userRepo;
        this.service = service;
    }

    @PostMapping("/user-init")
    public ResponseEntity<?> initalizeStorage(@RequestBody UserInitRequestDTO requestDTO){
        if(userRepo.existsById(requestDTO.getUserId())){
            return ResponseEntity.ok("User Already initialized");
        }
        service.initializeStorageForUser(requestDTO.getUserId(),requestDTO.getRealm());
        return  ResponseEntity.ok("User initialized!");
    }

}
