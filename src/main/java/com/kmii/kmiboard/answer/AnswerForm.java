package com.kmii.kmiboard.answer;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AnswerForm {

	@NotEmpty
	@Size(min=5, message="최소 5자 이상")
	private String content;

}
