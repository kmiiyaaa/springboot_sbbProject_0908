package com.kmii.kmiboard.question;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForm {  // 질문 글의 제목과 내용의 유효성 체크용 클래스
	
	@NotEmpty(message = "제목은 필수 항목 입니다") // 제목이 공란으로 들어오면 작동
	@Size(max=200, message="제목은 최대 200글자까지 허용됩니다.") //제목 최대 200글자까지 허용
	@Size(min = 5, message="제목은 최소 5글자까지 허용됩니다.")  // 제목 최소 5글자 이상 허용
	private String subject;
	
	@NotEmpty(message = "내용은 필수 항목 입니다")
	@Size(max=500, message="내용은 최대 500글자까지 허용됩니다.") // 내용 최대 500글자까지 사용
	@Size(min = 5, message="제목은 최소 5글자까지 허용됩니다.")  // 내용 최소 5글자 이상 허용
	private String content;
	
	

}
