package com.assistico.planner.dto.api;

import com.assistico.planner.utils.Points;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TaskResponse {
    Long id;

    String title;

    String Description;

    Points points;
}
