package com.studymavernspringboot.mustachajax.stompevery;

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
public class StompEveryChatDto {
    private Long id;
    private Long roomId;
    private String roomName;
    private String writer;
    private String msgTime;
    private String message;
}
