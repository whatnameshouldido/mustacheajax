package com.studymavernspringboot.mustachajax.board;

import com.studymavernspringboot.mustachajax.commons.dto.BaseDto;
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
public class BoardDto extends BaseDto implements IBoard {
    private Long id;
    private String name;
    private String content;
    private Integer viewQty;
    private Integer likeQty;
}
