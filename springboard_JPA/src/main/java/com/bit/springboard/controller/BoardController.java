package com.bit.springboard.controller;

import com.bit.springboard.common.FileUtils;
import com.bit.springboard.dto.BoardDTO;
import com.bit.springboard.dto.BoardFileDTO;
import com.bit.springboard.dto.ResponseDTO;
import com.bit.springboard.entity.Board;
import com.bit.springboard.entity.BoardFile;
import com.bit.springboard.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//화면단으로 이동할 때는 ModelAndView객체를 리턴해서 처리
@RestController
@RequestMapping("/board")
public class BoardController {
    private BoardService boardService;

    @Value("${file.path}")
    String attachPath;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/board-list")
    public ModelAndView getBoardList() {
        ModelAndView mv = new ModelAndView();

        List<Board> boardList = boardService.getBoardList();

        List<BoardDTO> boardDtoList = new ArrayList<BoardDTO>();

        for(Board b : boardList) {
            BoardDTO returnBoardDTO = BoardDTO.builder()
                                              .boardNo(b.getBoardNo())
                                              .boardTitle(b.getBoardTitle())
                                              .boardContent(b.getBoardContent())
                                              .boardWriter(b.getBoardWriter())
                                              .boardRegdate(b.getBoardRegdate().toString())
                                              .boardCnt(b.getBoardCnt())
                                              .build();

            boardDtoList.add(returnBoardDTO);
        }

        mv.addObject("boardList", boardDtoList);
        mv.setViewName("board/getBoardList.html");

        return mv;
    }

    @PostMapping("/board")
    public ResponseEntity<?> insertBoard(BoardDTO boardDTO,
                                         MultipartFile[] uploadFiles,
                                         HttpServletRequest request) {
        ResponseDTO<Map<String, String>> responseDTO =
                    new ResponseDTO<Map<String, String>>();
//        String attachPath =
//                request.getSession().getServletContext().getRealPath("/")
//                + "/upload/";

        File directory = new File(attachPath);

        if(!directory.exists()) {
            directory.mkdir();
        }

        List<BoardFile> uploadFileList = new ArrayList<BoardFile>();

        try {
            //BoardEntity에 지정한 boardRegdate의 기본값은
            //기본생성자 호출할 때만 기본값으로 지정되는데
            //builder()는 모든 매개변수를 갖는 생성자를 호출하기 때문에
            //boardRegdate의 값이 null값으로 들어간다.
            Board board = Board.builder()
                            .boardTitle(boardDTO.getBoardTitle())
                            .boardContent(boardDTO.getBoardContent())
                            .boardWriter(boardDTO.getBoardWriter())
                            .boardRegdate(LocalDateTime.now())
                            .build();
            System.out.println("========================"+board.getBoardRegdate());

            //파일처리
            for(int i = 0; i < uploadFiles.length; i++) {
                MultipartFile file = uploadFiles[i];

                if(file.getOriginalFilename() != null &&
                        !file.getOriginalFilename().equals("")) {
                    BoardFile boardFile = new BoardFile();

                    boardFile = FileUtils.parseFileInfo(file, attachPath);

                    boardFile.setBoard(board);

                    uploadFileList.add(boardFile);
                }
            }

            boardService.insertBoard(board, uploadFileList);

            Map<String, String> returnMap =
                    new HashMap<String, String>();

            returnMap.put("msg", "정상적으로 저장되었습니다.");

            responseDTO.setItem(returnMap);

            return ResponseEntity.ok().body(responseDTO);
        } catch(Exception e) {
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            responseDTO.setErrorMessage(e.getMessage());

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PutMapping("/board")
    public ResponseEntity<?> updateBoard(BoardDTO boardDTO) {
        ResponseDTO<Map<String, String>> responseDTO =
                new ResponseDTO<Map<String, String>>();

        try {
            Board board = Board.builder()
                            .boardNo(boardDTO.getBoardNo())
                            .boardTitle(boardDTO.getBoardTitle())
                            .boardContent(boardDTO.getBoardContent())
                            .boardWriter(boardDTO.getBoardWriter())
                            .boardRegdate(
                                    LocalDateTime.parse(
                                            boardDTO.getBoardRegdate()
                                    )
                            )
                            .boardCnt(boardDTO.getBoardCnt())
                            .build();

            boardService.updateBoard(board);

            Map<String, String> returnMap = new HashMap<String, String>();

            returnMap.put("msg", "정상적으로 수정되었습니다.");

            responseDTO.setItem(returnMap);

            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            responseDTO.setErrorMessage(e.getMessage());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @DeleteMapping("/board")
    public ResponseEntity<?> deleteBoard(BoardDTO boardDTO) {
        ResponseDTO<Map<String, String>> responseDTO =
                new ResponseDTO<Map<String, String>>();

        try {
            boardService.deleteBoard(boardDTO.getBoardNo());

            Map<String, String> returnMap = new HashMap<String, String>();

            returnMap.put("msg", "정상적으로 삭제되었습니다.");

            responseDTO.setItem(returnMap);

            return ResponseEntity.ok().body(responseDTO);
        } catch (Exception e) {
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            responseDTO.setErrorMessage(e.getMessage());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @GetMapping("/board/{boardNo}")
    public ModelAndView getBoard(@PathVariable int boardNo) {
        ModelAndView mv = new ModelAndView();

        Board board = boardService.getBoard(boardNo);

        BoardDTO returnBoardDTO = BoardDTO.builder()
                .boardNo(board.getBoardNo())
                .boardTitle(board.getBoardTitle())
                .boardContent(board.getBoardContent())
                .boardWriter(board.getBoardWriter())
                .boardRegdate(board.getBoardRegdate().toString())
                .boardCnt(board.getBoardCnt())
                .build();

        List<BoardFile> boardFileList = boardService.getBoardFileList(boardNo);

        List<BoardFileDTO> boardFileDTOList =
                            new ArrayList<BoardFileDTO>();

        for(BoardFile boardFile : boardFileList) {
            BoardFileDTO boardFileDTO = boardFile.EntityToDTO();
            boardFileDTOList.add(boardFileDTO);
        }

        mv.addObject("board", returnBoardDTO);
        mv.addObject("boardFileList", boardFileDTOList);
        mv.setViewName("board/getBoard.html");

        return mv;
    }

    @GetMapping("/insert-board-view")
    public ModelAndView insertBoardView() {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("board/insertBoard.html");

        return mv;
    }












}
