package com.bit.springboard.controller;

import com.bit.springboard.dto.BoardDTO;
import com.bit.springboard.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController //@Controller + @ResponseBody
@RequestMapping("/api")
public class ApiController {
    private BoardService boardService;

    //생성자 주입
    @Autowired
    public ApiController(BoardService boardService) {

        this.boardService = boardService;
    }

    @GetMapping("/testApi")
    public Map<String, String> testApi() {
        Map<String, String> returnMap = new HashMap<String, String>();

        returnMap.put("first", "one");
        returnMap.put("second", "two");

        return returnMap;
    }

    @GetMapping("/testApi2")
    public Map<String, Object> testApi2(int boardNo) {
        Map<String, Object> returnMap = new HashMap<String, Object>();

        returnMap.put("board", boardService.getBoard(boardNo));
        returnMap.put("boardList", boardService.getBoardList());
        List<Integer> intList = new ArrayList<Integer>();

        for(int i = 0; i < 100; i++) {
            intList.add(i);
        }

        returnMap.put("intList", intList);

        return returnMap;

    }


    @GetMapping("/board")
    public BoardDTO getBoard(int boardNo) {

        return boardService.getBoard(boardNo);
    }

    @GetMapping("/boardList")
    public List<BoardDTO> getBoardList() {

        return boardService.getBoardList();
    }

    @PostMapping("/board")
    public void insertBoard(BoardDTO boardDTO) {

        boardService.insertBoard(boardDTO);
    }

    @PutMapping("/board")
    public void updateBoard(BoardDTO boardDTO) {

        boardService.updateBoard(boardDTO);
    }

    @DeleteMapping("/board")
    public void deleteBoard(int boardNo) {
        boardService.deleteBoard(boardNo);
    }


    @GetMapping("/restfulapi")
    public ResponseEntity<?> restFulApi(int boardNo) {
        try {
            Map<String, Object> returnMap = new HashMap<String, Object>();

            returnMap.put("board", boardService.getBoard(boardNo));
            returnMap.put("boardList", boardService.getBoardList());

            List<Integer> intList = new ArrayList<Integer>();

            for(int i = 0; i < 100; i++) {
                intList.add(i);
            }

            returnMap.put("intList", intList);

            return ResponseEntity.ok().body(returnMap);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }


}
