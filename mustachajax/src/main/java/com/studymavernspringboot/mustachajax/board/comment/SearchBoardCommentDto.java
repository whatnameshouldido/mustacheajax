package com.studymavernspringboot.mustachajax.board.comment;

import com.studymavernspringboot.mustachajax.commons.dto.SearchAjaxDto;
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
public class SearchBoardCommentDto extends SearchAjaxDto {
    private String boardId;
    private String commentTbl;
    private String nickname;
    private Long commentId;
}
