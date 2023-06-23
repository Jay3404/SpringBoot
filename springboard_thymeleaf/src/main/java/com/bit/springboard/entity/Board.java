package com.bit.springboard.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Table(name = "T_BOARD")
@SequenceGenerator(
        name = "BoardSeqGenerator",
        sequenceName = "T_BOARD_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicUpdate
public class Board {
    //컬럼 정의
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "BoardSeqGenerator"
    )
    private int boardNo;
    private String boardTitle;
    private String boardContent;
    private String boardWriter;
    private LocalDateTime boardRegdate = LocalDateTime.now();

    @Column(name = "BOARD_CNT", nullable = false)
    private int boardCnt = 0;

    @Transient
    private String searchCondition;
    @Transient
    private String searchKeyword;


}
