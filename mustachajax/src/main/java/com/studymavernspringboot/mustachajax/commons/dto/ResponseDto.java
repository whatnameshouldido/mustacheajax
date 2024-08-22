package com.studymavernspringboot.mustachajax.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {
    private ResponseCode responseCode;
    private String message;
    private Object responseData;
}