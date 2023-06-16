package com.bit.springboard.dto;

import lombok.*;

@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor
public class BoardDTO {

    private int boardNo;

    private String boardTitle;

    private String boardContent;

    private String boardWriter;

    private String boardRegDate;

    private int boardCnt;


}
