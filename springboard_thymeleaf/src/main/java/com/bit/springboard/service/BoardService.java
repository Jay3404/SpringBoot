package com.bit.springboard.service;

import com.bit.springboard.dto.BoardDTO;

import java.util.List;

public interface BoardService {
    BoardDTO getBoard(int boardNo);

    List<BoardDTO> getBoardList();

    void insertBoard(BoardDTO boardDTO);

    void updateBoard(BoardDTO boardDTO);

    void deleteBoard(int boardNo);
}
