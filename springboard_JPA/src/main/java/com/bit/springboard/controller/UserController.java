package com.bit.springboard.controller;

import com.bit.springboard.dto.ResponseDTO;
import com.bit.springboard.dto.UserDTO;
import com.bit.springboard.entity.User;
import com.bit.springboard.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login-view")
    public ModelAndView loginView() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("user/login.html");
        return mv;
    }

    @GetMapping("/join-view")
    public ModelAndView joinView() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("user/join.html");
        return mv;
    }

    @PostMapping("/id-check")
    public ResponseEntity<?> idCheck(UserDTO userDTO) {
        ResponseDTO<Map<String, String>> responseDTO =
                            new ResponseDTO<>();

        try {
            //중복된 아이디인지 체크
            User user = userService.idCheck(userDTO.getUserId());

            //메시지를 담을 맵 선언
            Map<String, String> returnMap = new HashMap<>();

            //조건문으로 메시지를 다르게 리턴
            if(user == null) {
                returnMap.put("idCheckMsg", "idOk");
            } else {
                returnMap.put("idCheckMsg", "idFail");
            }

            responseDTO.setItem(returnMap);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(responseDTO);
        } catch(Exception e) {
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/join")
    public ModelAndView join(UserDTO userDTO) {
        ModelAndView mv = new ModelAndView();

        User user = userDTO.DTOToEntity();

        userService.join(user);

        mv.setViewName("user/login.html");
        return mv;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(UserDTO userDTO, HttpSession session){
        ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>();

        try {
            //메시지를 담을 맵 선언
            Map<String, String> returnMap = new HashMap<>();

            //1. 아이디가 존재하는지 체크, 아이디가 없으면 msg에 notExistId
            User user = userService.idCheck(userDTO.getUserId());

            if(user == null){
                returnMap.put("msg", "notExistId");
            } else {
                if(!user.getUserPw().equals(userDTO.getUserPw())) {
                    returnMap.put("msg", "wrongPw");
                } else {
                    returnMap.put("msg", user.getUserId());

                    UserDTO loginUser = user.EntityToDTO();

                    session.setAttribute("loginUser", loginUser);
                }
            }

            //2. 비밀번호가 맞는지 체크, 비밀번호가 틀리면 msg에 "wrongPw"

            //3. 다 맞았을 경우 ruturnMap에 msg로 userId 담기


            responseDTO.setItem(returnMap);
            responseDTO.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok().body(responseDTO);
        } catch(Exception e) {
            responseDTO.setErrorMessage(e.getMessage());
            responseDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }

}
