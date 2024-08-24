package com.cooksys.quiz_api.services;

import java.util.List;

import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;
import com.cooksys.quiz_api.entities.Question;

public interface QuizService {

  List<QuizResponseDto> getAllQuizzes();

  QuizResponseDto createQuiz(QuizRequestDto quizRequestDto);

  QuizResponseDto deleteQuiz(long id);

  QuizResponseDto renameQuiz(long id, String newName);

  QuestionResponseDto getRandomQuestion(long id);

  QuizResponseDto addQuestion(long id, QuestionRequestDto questionRequestDto);

  QuestionResponseDto deleteQuestion(long id, long questionId);

}
