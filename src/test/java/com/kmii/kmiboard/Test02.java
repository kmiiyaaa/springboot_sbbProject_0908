package com.kmii.kmiboard;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kmii.kmiboard.entity.Question;
import com.kmii.kmiboard.repository.QuestionRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class Test02 {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Test
	@DisplayName("질문 게시판 제목 수정하기")
	public void testJpa1() {
		Optional<Question> optionalQuestion = questionRepository.findById(3);  // 글번호가 3번인 레코드 반환
		assertTrue(optionalQuestion.isPresent()); // 기본키로 가져온 레코드가 존재하면 true -> test통과 
		//기본키로 가져온 레코드가 존재하지 않으면 false -> 테스트 종료(실패)
		Question question = optionalQuestion.get();
		question.setSubject("수정된 제목!!!");
		
		this.questionRepository.save(question); //다시 수정된 내용 넣어주기 , 위에 @Transactional 사용하면 안써도 된다(but, 커밋은 잘 안된다)
		 // update가 따로 없어서 기본키로 조회 -> 내용 변경 (setter로)-> 다시 넣어줌 
		
	}

}
