package com.bit.springboard.mapper;

import com.bit.springboard.dto.BoardDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BoardMapper {
    @Select("SELECT BOARD_NO" +
            "     , BOARD_TITLE" +
            "     , BOARD_CONTENT" +
            "     , BOARD_WRITER" +
            "     , BOARD_REGDATE" +
            "     , BOARD_CNT" +
            "   FROM T_BOARD" +
            "   WHERE BOARD_NO = #{boardNo}")
    BoardDTO getBoard(int boardNo);
}
