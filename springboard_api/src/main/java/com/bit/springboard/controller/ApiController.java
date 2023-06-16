package com.bit.springboard.controller;

import com.bit.springboard.dto.BoardDTO;
import com.bit.springboard.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController // @Controller + @ResponseBody
@RequestMapping("/api")
public class ApiController {
    private BoardService boardService;

    //생성자 주입
    @Autowired
    public ApiController(BoardService boardService ){
        this.boardService = boardService;
    }

    @GetMapping("/testApi")
    public Map<String, String> testApi() {
        Map<String, String> returnMap = new HashMap<String, String>();

        returnMap.put("first", "one");
        returnMap.put("second", "two");

        return returnMap;

    }


    @GetMapping("/board")
    public BoardDTO getboard(int boardNo) {
        return boardService.getBoard(boardNo);
    }
}
