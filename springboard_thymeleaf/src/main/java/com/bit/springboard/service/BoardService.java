package com.bit.springboard.service;

import com.bit.springboard.entity.Board;

import java.util.List;

public interface BoardService {
    Board getBoard(int boardNo);

    List<Board> getBoardList();

    void insertBoard(Board board);

    void updateBoard(Board board);

    void deleteBoard(int boardNo);
}
