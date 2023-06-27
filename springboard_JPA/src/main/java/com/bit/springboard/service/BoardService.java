package com.bit.springboard.service;

import com.bit.springboard.entity.Board;
import com.bit.springboard.entity.BoardFile;

import java.util.List;

public interface BoardService {
    Board getBoard(int boardNo);

    List<Board> getBoardList();

    void insertBoard(Board board, List<BoardFile> uploadFileList);

    void updateBoard(Board board);

    void deleteBoard(int boardNo);

    List<BoardFile> getBoardFileList(int boardNo);
}
