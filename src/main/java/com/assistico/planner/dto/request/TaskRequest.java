package com.assistico.planner.dto.request;


import com.assistico.planner.utils.Points;
import lombok.*;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@Builder
public class TaskRequest {
    Long id;

    @NotBlank(message = "title cannot be blank")
    @Length(min = 3, max = 20)
    String title;

    @Length(max = 500)
    String description;

    Points points;

}
