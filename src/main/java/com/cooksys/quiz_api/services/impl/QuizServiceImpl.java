package com.cooksys.quiz_api.services.impl;

import java.util.List;
import java.util.Random;

import com.cooksys.quiz_api.dtos.QuestionRequestDto;
import com.cooksys.quiz_api.dtos.QuestionResponseDto;
import com.cooksys.quiz_api.dtos.QuizRequestDto;
import com.cooksys.quiz_api.dtos.QuizResponseDto;
import com.cooksys.quiz_api.entities.Answer;
import com.cooksys.quiz_api.entities.Question;
import com.cooksys.quiz_api.entities.Quiz;
import com.cooksys.quiz_api.mappers.QuestionMapper;
import com.cooksys.quiz_api.mappers.QuizMapper;
import com.cooksys.quiz_api.repositories.AnswerRepository;
import com.cooksys.quiz_api.repositories.QuestionRepository;
import com.cooksys.quiz_api.repositories.QuizRepository;
import com.cooksys.quiz_api.services.QuizService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {

  private final QuizRepository quizRepository;
  private final QuizMapper quizMapper;
  private final AnswerRepository answerRepository;
  private final QuestionRepository questionRepository;
  private final QuestionMapper questionMapper;

  @Override
  public List<QuizResponseDto> getAllQuizzes() {
    return quizMapper.entitiesToDtos(quizRepository.findAll());
  }

  @Override
  public QuizResponseDto createQuiz(QuizRequestDto quizRequestDto) {
    Quiz quizToSave = quizMapper.quizRequestDtoToQuiz(quizRequestDto);


    quizRepository.saveAndFlush(quizToSave);
    for(Question question : quizToSave.getQuestions()) {
      question.setQuiz(quizToSave);
    }
    questionRepository.saveAllAndFlush(quizToSave.getQuestions());

    for (Question question : quizToSave.getQuestions()) {
      for (Answer answer : question.getAnswers()) {
        answer.setQuestion(question);
      }
      answerRepository.saveAllAndFlush(question.getAnswers());
    }

    return quizMapper.entityToDto(quizToSave);
  }

  @Override
  public QuizResponseDto deleteQuiz(long id){
    Quiz quizToDelete = quizRepository.getById(id);

    for(Question question : quizToDelete.getQuestions()) {
      answerRepository.deleteAll(question.getAnswers());
      questionRepository.delete(question);
    }

    quizRepository.delete(quizToDelete);
    return quizMapper.entityToDto(quizToDelete);
  }

  @Override
  public QuizResponseDto renameQuiz(long id, String newName) {
    Quiz quizToRename = quizRepository.getById(id);
    quizToRename.setName(newName);
    quizRepository.saveAndFlush(quizToRename);
    return quizMapper.entityToDto(quizToRename);
  }

  @Override
  public QuestionResponseDto getRandomQuestion(long id) {
    Quiz quiz = quizRepository.getById(id);
    List<Question> questions = quiz.getQuestions();
    Random rand = new Random();
    Question question = questions.get(rand.nextInt(questions.size()));
    return questionMapper.entityToDto(question);
  };

  @Override
  public QuizResponseDto addQuestion(long id, QuestionRequestDto questionRequestDto) {
    Quiz quiz = quizRepository.getById(id);
    Question question = questionMapper.dtoToEntity(questionRequestDto);

    quiz.getQuestions().add(question);
    question.setQuiz(quiz);

    question = questionRepository.saveAndFlush(question);

    for(Answer answer : question.getAnswers()) {
      answer.setQuestion(question);
    }
    answerRepository.saveAllAndFlush(question.getAnswers());
    return quizMapper.entityToDto(quiz);
  }

  @Override
  public QuestionResponseDto deleteQuestion(long id, long questionId) {
    Quiz quiz = quizRepository.getById(id);
    Question questionToDelete = new Question();
    for(Question question : quiz.getQuestions()) {
      if(question.getId() == questionId) {
       questionToDelete = questionRepository.findById(questionId).get();
      }
    }

    answerRepository.deleteAll(questionToDelete.getAnswers());
    questionRepository.delete(questionToDelete);
    return questionMapper.entityToDto(questionToDelete);

  }

}
