package com.cooksys.quiz_api.dtos;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data

public class QuestionRequestDto {
    private long id;
    private String text;
    private List<AnswerResponseDto> answers;
}
