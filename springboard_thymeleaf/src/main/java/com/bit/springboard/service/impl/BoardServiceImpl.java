package com.bit.springboard.service.impl;

import com.bit.springboard.entity.Board;
import com.bit.springboard.mapper.BoardMapper;
import com.bit.springboard.repository.BoardRepository;
import com.bit.springboard.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {
    private BoardMapper boardMapper;

    private BoardRepository boardRepository;

    //생성자 주입
    @Autowired
    public BoardServiceImpl(BoardMapper boardMapper, BoardRepository boardRepository) {

        this.boardMapper = boardMapper;
        this.boardRepository = boardRepository;
    }

    @Override
    public Board getBoard(int boardNo) {

        return boardRepository.findById(boardNo).get();
    }

    @Override
    public List<Board> getBoardList() {
        return boardRepository.findAll();
    }

    @Override
    public void insertBoard(Board board) {

        boardRepository.save(board);
    }

    @Override
    public void updateBoard(Board board) {
        boardRepository.save(board);
    }

    @Override
    public void deleteBoard(int boardNo) {

        boardRepository.deleteById(boardNo);
    }
}
