package com.praj.secureVault.health.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class HealthDto {


    private  String status;
    private String service;
}
