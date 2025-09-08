package com.kmii.kmiboard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kmii.kmiboard.entity.Answer;
import com.kmii.kmiboard.entity.Question;
import com.kmii.kmiboard.repository.AnswerRepository;
import com.kmii.kmiboard.repository.QuestionRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class Test02 {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
//	@Test
//	@DisplayName("질문 게시판 제목 수정하기 테스트")
//	public void testJpa1() {
//		Optional<Question> optionalQuestion = questionRepository.findById(3);  // 글번호가 3번인 레코드 반환
//		assertTrue(optionalQuestion.isPresent()); // 기본키로 가져온 레코드가 존재하면 true -> test통과 
//		//기본키로 가져온 레코드가 존재하지 않으면 false -> 테스트 종료(실패)
//		Question question = optionalQuestion.get();
//		question.setSubject("수정된 제목!!!");
//		
//		this.questionRepository.save(question); //다시 수정된 내용 넣어주기 , 위에 @Transactional 사용하면 안써도 된다(but, 커밋은 잘 안된다)
//		 // update가 따로 없어서 기본키로 조회 -> 내용 변경 (setter로)-> 다시 넣어줌 
//		
//	}
	
//	@Test
//	@DisplayName("질문 게시판 글 삭제하기 테스트")
//	public void testJpa2() {
//		assertEquals(2, questionRepository.count());
//		//questionRepository.count() -> 모든 행(레코드)의 갯수 반환
//		Optional<Question> qOptional = questionRepository.findById(3);  // 3번글 가져오기
//		assertTrue(qOptional.isPresent()); // 3번글의 존재 여부
//		Question question = qOptional.get();
//		questionRepository.delete(question); //delete(entity) -> 해당 글이 삭제
//		assertEquals(1, questionRepository.count()); // 지워지고 글 갯수가 1개인걸 확인
//		
//	}
	
//	@Test
//	@DisplayName("답변 게시판 글 생성하기 테스트")
//	public void testJpa3() {
//		// 답변 -> 질문글의 번호를 준비
//		Optional<Question> qOptional  = questionRepository.findById(4);
//		assertTrue(qOptional.isPresent());
//		Question question = qOptional.get();
//		
//		Answer answer = new Answer();
//		answer.setContent("네 자동으로 생성됩니다."); // 답변 내용 넣어주기
//		answer.setCreatedate(LocalDateTime.now());  // 현재 시간 넣어주기
//		answer.setQuestion(question); // 답변이 달릴 질문글을 필드로 넣어주기 (글 하나) 
//		answerRepository.save(answer);
//		
//	}
	
//	@Test
//	@DisplayName("답변 게시판 글 조회 테스트")
//	public void testJpa4() {
//		Optional<Answer> aOptional = answerRepository.findById(1);  // 답변글 테이블에서 1번글 가져오기
//		assertTrue(aOptional.isPresent());  // 해당 글 번호의 답변이 존재하는지 확인 테스트
//		
//		Answer answer = aOptional.get();
//		assertEquals(4, answer.getQuestion().getId());  // 부모글(질문글)의 번호로 확인 테스트 4번글의 댓글이니까 확인
//		
//	}
	
	@Test
	@DisplayName("질문 글을 통해 답변 글들 조회 테스트")
	@Transactional  
	public void testJpa5() {
		//질문글 가져오기(4번)
		Optional<Question> qOptional = questionRepository.findById(4); 
		assertTrue(qOptional.isPresent()); // 4번글의 존재 여부
		Question question = qOptional.get();
		
		List<Answer> answerList = question.getAnswerList(); // 해당 질문글에 달린 답변들의 리스트
		//게으른 초기화 문제로 오류 -> question entity가 닫힌 후에 초기화 시도
		//테스트 과정에서만 발생 -> @Transactional 사용하여 에러 막을 수 있음
		
		assertEquals(1, answerList.size());
		assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
		
		// QuestionRepository가 findById 메서드를 통해 Question 객체를 조회하고 나면 DB 세션이 끊어지기 때문 -> answer처리할때는 세션이 종료되어 쓸수 없음 
		// answerList는 앞서 q 객체를 조회할 때가 아니라 q.getAnswerList( ) 메서드를 호출하는 시점에 가져오기 때문에 오류가 발생
		//@Transactional 애너테이션을 사용하면 메서드가 종료될 때까지 DB 세션이 유지됨.
		 
	}

}
