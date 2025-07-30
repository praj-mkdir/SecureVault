package com.praj.secureVault.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="user_metadata")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserStorage {
    @Id
    private String userId;

    private Long allocatedSpace;
    private Long usedSpace;      // in bytes
    private String storageLocation; //s3 or local
    private String directoryPath; // path -- for local or prefix -- s3
}
