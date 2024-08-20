package com.studymavernspringboot.mustachajax.commons.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {
    private String message;
    private Object result;
}