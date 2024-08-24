package com.cooksys.quiz_api.controllers;

import java.util.List;

import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;
import com.cooksys.quiz_api.entities.Question;
import com.cooksys.quiz_api.mappers.AnswerMapper;
import com.cooksys.quiz_api.services.QuizService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz")
public class QuizController {

  private final QuizService quizService;
  private final AnswerMapper answerMapper;

  @GetMapping
  public List<QuizResponseDto> getAllQuizzes() {
    return quizService.getAllQuizzes();
  }
  
  // TODO: Implement the remaining 6 endpoints from the documentation.

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public QuizResponseDto createQuiz (@RequestBody QuizRequestDto quizRequestDto){ return quizService.createQuiz(quizRequestDto); }

  @DeleteMapping("/{id}")
  public QuizResponseDto deleteQuiz (@PathVariable long id) {
    return quizService.deleteQuiz(id);
  }

  @PatchMapping("/{id}/rename/{newName}")
  public QuizResponseDto renameQuiz (@PathVariable long id, @PathVariable String newName) {
    return quizService.renameQuiz(id, newName);
  }

  @GetMapping("/{id}/random")
  public QuestionResponseDto getRandomQuestion (@PathVariable long id) {
    return quizService.getRandomQuestion(id);
  }

  @PatchMapping("/{id}/add")
  public QuizResponseDto addQuestion (@PathVariable long id, @RequestBody QuestionRequestDto questionRequestDto) {
    return quizService.addQuestion(id, questionRequestDto);
  }

  /*@PatchMapping("/{id}/add")
  public Question addQuestion (@PathVariable long id, @RequestBody QuestionRequestDto questionRequestDto) {
    return quizService.addQuestion(id, questionRequestDto);
  }*/

  @DeleteMapping("/{id}/delete/{questionId}")
  public QuestionResponseDto deleteQuestion (@PathVariable long id, @PathVariable long questionId) {
    return quizService.deleteQuestion(id, questionId);
  }

}
