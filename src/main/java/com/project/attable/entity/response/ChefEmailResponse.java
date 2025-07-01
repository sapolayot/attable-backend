package com.project.attable.entity.response;

import com.project.attable.entity.Chef;
import com.project.attable.entity.RejectChef;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChefEmailResponse {
    private boolean success;
    private RejectChef reject;
    private Chef chef;
}
