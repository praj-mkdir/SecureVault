package com.praj.secureVault.service;

import com.praj.secureVault.model.UserStorage;
import com.praj.secureVault.repository.UserMetaDataRepository;
import com.praj.secureVault.util.FileUtilFuncitons;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;


@Service
@Slf4j
public class UserInitService {
    @Value("${file.upload.strategy}")
    public static String storageLocation;
    private final UserMetaDataRepository userRepo;
    private final FileUtilFuncitons fileUtil;
    public UserInitService(UserMetaDataRepository userRepo, FileUtilFuncitons fileUtil) {
        this.userRepo = userRepo;
        this.fileUtil = fileUtil;
    }

    @Value("${file.default.storage.limit.mb:100}")
    private Long defaultStorageLimit;


    public void initializeStorageForUser(String userId, String realm){

        String resolvedPath = fileUtil.resolveUserPath();
        log.info("ResolvedPath --- " + resolvedPath);
        userRepo.save(new UserStorage(userId, defaultStorageLimit, 1L,storageLocation,resolvedPath));
    }

}
