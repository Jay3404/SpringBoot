package com.bit.springboard.controller;

import com.bit.springboard.dto.BoardDTO;
import com.bit.springboard.dto.ResponseDTO;
import com.bit.springboard.service.BoardService;
import jakarta.servlet.http.HttpServletResponse;
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
    public ResponseEntity<?> getBoard(int boardNo) {
        ResponseDTO<BoardDTO> responseDTO = new ResponseDTO<BoardDTO>();

        try {
            responseDTO.setItem(boardService.getBoard(boardNo));

            return ResponseEntity.ok().body(responseDTO);

        } catch (Exception e) {

            responseDTO.setErrorMessage(e.getMessage());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @GetMapping("/boardList")
    public ResponseEntity<?> getBoardList() {

        ResponseDTO<BoardDTO> responseDTO = new ResponseDTO<BoardDTO>();

        try{
            responseDTO.setItems(boardService.getBoardList());

            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {

            responseDTO.setErrorMessage(e.getMessage());
            return ResponseEntity.badRequest().body(responseDTO);
        }

    }

    @PostMapping("/board")
    public ResponseEntity<?> insertBoard(BoardDTO boardDTO) {
        Map<String, Object> returnMap = new HashMap<String, Object>();

        ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<Map<String, Object>>();

        try {

            boardService.insertBoard(boardDTO);

            returnMap.put("msg", "입력완료되었습니다.");

            responseDTO.setItem(returnMap);

            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {

            responseDTO.setErrorMessage(e.getMessage());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PutMapping("/board")
    public ResponseEntity<?> updateBoard(BoardDTO boardDTO) {

        ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<Map<String, Object>>();

        Map<String, Object> returnMap = new HashMap<String, Object>();

        try {

            if (boardService.getBoard(boardDTO.getBoardNo()) == null){
                returnMap.put("msg", "없는 게시물입니다.");
            } else {
                boardService.updateBoard(boardDTO);
                returnMap.put("msg", "수정완료되었습니다.");
            }

            responseDTO.setItem(returnMap);

            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {

            responseDTO.setErrorMessage(e.getMessage());
            return ResponseEntity.badRequest().body(responseDTO);
        }

    }

    @DeleteMapping("/board")
    public ResponseEntity<?> deleteBoard(int boardNo) {

        ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<Map<String, Object>>();
        Map<String, Object> returnMap = new HashMap<String, Object>();

        try {

            if (boardService.getBoard(boardNo) == null){
                returnMap.put("msg", "없는 게시물입니다.");
            } else {
                boardService.deleteBoard(boardNo);
                returnMap.put("msg", "게시물이 삭제되었습니다.");
            }

            responseDTO.setItem(returnMap);

            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            responseDTO.setErrorMessage(e.getMessage());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }


    @GetMapping("/restfulapi")
    public ResponseEntity<?> restFulApi(int boardNo, HttpServletResponse response) {

        ResponseDTO<Map<String, Object>> responseDTO = new ResponseDTO<Map<String, Object>>();

        try {

            Map<String, Object> returnMap = new HashMap<String, Object>();

            returnMap.put("board", boardService.getBoard(boardNo));
            returnMap.put("boardList", boardService.getBoardList());

            List<Integer> intList = new ArrayList<Integer>();

            for(int i = 0; i < 100; i++) {
                intList.add(i);
            }

            returnMap.put("intList", intList);
            responseDTO.setItem(returnMap);
            responseDTO.setStatusCode(response.getStatus());

            return ResponseEntity.ok().body(returnMap);

        } catch (Exception e) {
            responseDTO.setStatusCode(response.getStatus());

            responseDTO.setErrorMessage(e.getMessage());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }


}
