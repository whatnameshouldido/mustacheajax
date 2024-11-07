package com.studymavernspringboot.mustachajax.stomp;

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
public class StompRoomDto {
    private String roomName;
    private String roomId;
    private String writer;
    private Integer count;
}
