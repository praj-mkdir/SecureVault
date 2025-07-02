package com.praj.secureVault.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name ="file_metadata")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FileMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fileName;

    private Long size;

    @Column(name = "storage_path")
    private  String storagePath;

    @Column(name = "uploaded_by")
    private  String uploadedBy;

    @Column(name = "uploaded_at")
    private String uploadedAt;

    @Column(name = "checkSum")
    private String checksumSha256;

    @Column(name = "trace_id")
    private String traceId;


}
