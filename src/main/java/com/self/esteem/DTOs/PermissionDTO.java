package com.self.esteem.DTOs;

import lombok.Data;

import java.util.List;

@Data
public class PermissionDTO {
    private Long userId;
    private List<Long> questionId;
}
