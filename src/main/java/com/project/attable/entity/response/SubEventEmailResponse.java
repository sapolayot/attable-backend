package com.project.attable.entity.response;

import com.project.attable.entity.Chef;
import com.project.attable.entity.RejectChef;
import com.project.attable.entity.RejectChefCancel;
import com.project.attable.entity.SubEvent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubEventEmailResponse {
    private boolean success;
    private RejectChefCancel reject;
    private SubEvent subEvent;
}
