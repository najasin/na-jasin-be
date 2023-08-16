package com.najasin.domain.user.dto;

import com.najasin.domain.dto.AnswerDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PutAnswer {
    private List<AnswerDTO> answers;
    private String userType;
}
