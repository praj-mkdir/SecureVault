package com.praj.secureVault.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name ="file_metadata")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
public class FileMetadata {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private String id;
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

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "generated_FileName")
    private String generated_FileName;

    @Column(name="s3_key")
    private String s3_Key;

    @Column(name = "status")
    private String status;


}

