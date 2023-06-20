package com.bit.springboard.service.impl;

import com.bit.springboard.dto.BoardDTO;
import com.bit.springboard.mapper.BoardMapper;
import com.bit.springboard.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {
    private BoardMapper boardMapper;

    //생성자 주입
    @Autowired
    public BoardServiceImpl(BoardMapper boardMapper) {

        this.boardMapper = boardMapper;
    }

    @Override
    public BoardDTO getBoard(int boardNo) {

        return boardMapper.getBoard(boardNo);
    }

    @Override
    public List<BoardDTO> getBoardList() {
        return boardMapper.getBoardList();
    }

    @Override
    public void insertBoard(BoardDTO boardDTO) {
        boardMapper.insertBoard(boardDTO);
    }

    @Override
    public void updateBoard(BoardDTO boardDTO) {
        boardMapper.updateBoard(boardDTO);
    }

    @Override
    public void deleteBoard(int boardNo) {
        boardMapper.deleteBoard(boardNo);
    }
}
