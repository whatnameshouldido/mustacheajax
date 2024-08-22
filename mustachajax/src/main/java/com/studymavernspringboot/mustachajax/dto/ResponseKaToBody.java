package com.studymavernspringboot.mustachajax.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseKaToBody {
    private ResponseKaToBodyItems items;
}
