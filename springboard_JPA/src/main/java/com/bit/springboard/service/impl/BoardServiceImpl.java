package com.bit.springboard.service.impl;

import com.bit.springboard.entity.Board;
import com.bit.springboard.entity.BoardFile;
import com.bit.springboard.mapper.BoardMapper;
import com.bit.springboard.repository.BoardFileRepository;
import com.bit.springboard.repository.BoardRepository;
import com.bit.springboard.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {
    private BoardMapper boardMapper;

    private BoardRepository boardRepository;

    private BoardFileRepository boardFileRepository;

    //생성자 주입
    @Autowired
    public BoardServiceImpl(BoardMapper boardMapper,
                            BoardRepository boardRepository,
                            BoardFileRepository boardFileRepository) {
        this.boardMapper = boardMapper;
        this.boardRepository = boardRepository;
        this.boardFileRepository = boardFileRepository;
    }

    @Override
    public Board getBoard(int boardNo) {
        if(boardRepository.findById(boardNo).isEmpty())
            return null;

        return boardRepository.findById(boardNo).get();
    }

    @Override
    public List<Board> getBoardList() {
        return boardRepository.findAll();
    }

    @Override
    public void insertBoard(Board board, List<BoardFile> uploadFileList) {
        boardRepository.save(board);
        //변경사항 커밋 후 저징
        boardRepository.flush();

        for(BoardFile boardFile : uploadFileList) {
            boardFile.setBoard(board);

            int boardFileNo = boardFileRepository.findMaxFileNo(board.getBoardNo());
            boardFile.setBoardFileNo(boardFileNo);

            boardFileRepository.save(boardFile);
        }
    }

    @Override
    public void updateBoard(Board board) {
        boardRepository.save(board);
    }

    @Override
    public void deleteBoard(int boardNo) {
        boardRepository.deleteById(boardNo);
    }

    @Override
    public List<BoardFile> getBoardFileList(int boardNo) {

        return boardFileRepository.findByBoardBoardNo(boardNo);
    }
}
