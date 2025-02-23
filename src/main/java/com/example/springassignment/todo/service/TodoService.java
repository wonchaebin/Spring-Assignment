package com.example.springassignment.todo.service;

import com.example.springassignment.member.dto.MemberSaveResponseDto;
import com.example.springassignment.member.entity.Member;
import com.example.springassignment.member.repository.MemberRepository;
import com.example.springassignment.todo.dto.TodoResponseDto;
import com.example.springassignment.todo.dto.TodoSaveRequestDto;
import com.example.springassignment.todo.dto.TodoUpdateRequestDto;
import com.example.springassignment.todo.dto.TodoUpdateResponseDto;
import com.example.springassignment.todo.entity.Todo;
import com.example.springassignment.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public MemberSaveResponseDto save(Long memberId, TodoSaveRequestDto dto) {

        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new RuntimeException("사용자를 찾을 수 없습니다.")
        );

        Todo todo = new Todo(
                dto.getTitle(),
                dto.getContent(),
                member
        );
        Todo savedTodo = todoRepository.save(todo);
        return new MemberSaveResponseDto(
                savedTodo.getId(),
                savedTodo.getTitle(),
                savedTodo.getContent(),
                member.getId(),
                member.getEmail()
        );
    }

    @Transactional(readOnly = true)
    public List<TodoResponseDto> findAll() {
        List<Todo> todos = todoRepository.findAll();
        List<TodoResponseDto> dtos = new ArrayList<>();
        for (Todo todo : todos) {
            dtos.add(new TodoResponseDto(
                    todo.getId(),
                    todo.getTitle(),
                    todo.getContent()
            ));
        }
        return dtos;
    }

    @Transactional(readOnly = true)
    public TodoResponseDto findById(Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new IllegalStateException("Todo가 존재하지 않습니다.")
        );
        return new TodoResponseDto(
                todo.getId(),
                todo.getTitle(),
                todo.getContent()
        );
    }

    @Transactional
    public TodoUpdateResponseDto update(Long memberId, Long todoId, TodoUpdateRequestDto dto) {

        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalStateException("사용자가 존재하지 않습니다.")
        );

        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new IllegalStateException("Todo가 존재하지 않습니다.")
        );

        if (!todo.getMember().getId().equals(member.getId())) {
            throw new IllegalStateException("Todo 작성자가 아닙니다.");
        }

        todo.update(dto.getTitle(), dto.getContent());
        return new TodoUpdateResponseDto(
                todo.getId(),
                todo.getTitle(),
                todo.getContent()
        );
    }

    @Transactional
    public void deleteById(Long memberId, Long todoId) {

        Member member = memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalStateException("사용자를 찾을 수 없습니다.")
        );

        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new IllegalStateException("Todo가 존재하지 않습니다.")
        );

        if (!todo.getMember().getId().equals(member.getId())) {
            throw new IllegalStateException("Todo 작성자가 아닙니다.");
        }

        todoRepository.deleteById(todoId);
    }
}
