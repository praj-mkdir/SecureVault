package com.praj.secureVault.repository;

import com.praj.secureVault.model.UserStorage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMetaDataRepository extends JpaRepository<UserStorage, String> {
}
