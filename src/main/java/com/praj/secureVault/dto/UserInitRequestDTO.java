package com.praj.secureVault.dto;


import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInitRequestDTO {

    private String userId;
    private String realm;
}
