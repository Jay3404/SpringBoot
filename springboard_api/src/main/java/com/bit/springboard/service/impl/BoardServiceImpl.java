package com.bit.springboard.service.impl;

import com.bit.springboard.dto.BoardDTO;
import com.bit.springboard.mapper.BoardMapper;
import com.bit.springboard.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
