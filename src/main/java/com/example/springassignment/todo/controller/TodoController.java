package com.example.springassignment.todo.controller;

import com.example.springassignment.common.consts.Const;
import com.example.springassignment.member.dto.MemberSaveRequestDto;
import com.example.springassignment.member.dto.MemberSaveResponseDto;
import com.example.springassignment.member.service.MemberService;
import com.example.springassignment.todo.dto.TodoResponseDto;
import com.example.springassignment.todo.dto.TodoSaveRequestDto;
import com.example.springassignment.todo.dto.TodoUpdateRequestDto;
import com.example.springassignment.todo.dto.TodoUpdateResponseDto;
import com.example.springassignment.todo.entity.Todo;
import com.example.springassignment.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;
    private final MemberService memberService;

    @PostMapping("/todos")
    public ResponseEntity<MemberSaveResponseDto> save(
            @SessionAttribute(name = Const.LOGIN_MEMBER) Long memberId,
            @RequestBody TodoSaveRequestDto dto
    ) {
        return ResponseEntity.ok(todoService.save(memberId, dto));
    }

    @GetMapping("/todos")
    public ResponseEntity<List<TodoResponseDto>> getAll() {
        return ResponseEntity.ok(todoService.findAll());
    }

    @GetMapping("/todos/{todoId}")
    public ResponseEntity<TodoResponseDto> getOne(@PathVariable Long todoId) {
        return ResponseEntity.ok(todoService.findById(todoId));
    }

    @PutMapping("/todos/{todoId}")
    public ResponseEntity<TodoUpdateResponseDto> update(
            @SessionAttribute(name = Const.LOGIN_MEMBER) Long memberId,
            @PathVariable Long todoId,
            @RequestBody TodoUpdateRequestDto dto
    ) {
        return ResponseEntity.ok(todoService.update(memberId, todoId, dto));
    }

    @DeleteMapping("/todos/{todoId}")
    public  void delete(
            @SessionAttribute(name = Const.LOGIN_MEMBER) Long memberId,
            @PathVariable Long todoId
    ) {
        todoService.deleteById(memberId, todoId);
    }
}
