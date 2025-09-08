package com.kmii.kmiboard;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kmii.kmiboard.question.Question;
import com.kmii.kmiboard.question.QuestionRepository;

@SpringBootTest
public class Test01 {
	
	@Autowired
	private QuestionRepository questionRepository;
	
//	@Test
//	@DisplayName("question 테이블에 질문글 저장하기")
//	public void testJpa1() {
//		Question q1 = new Question();
//		q1.setSubject("sbb가 무엇인가요?"); //질문 제목
//		q1.setContent("sbb에 대해 알고 싶습니다"); // 질문 내용
//		q1.setCreatedate(LocalDateTime.now()); // 현재 시간 넣기
//		//q1 -> entity 생성 완료
//		questionRepository.save(q1);
//		
//		
//		Question q2 = new Question();
//		q2.setSubject("스프링부트 모델이 무엇인가요?"); //질문 제목
//		q2.setContent("id는 자동 생성되는게 맞나요"); // 질문 내용
//		q2.setCreatedate(LocalDateTime.now()); // 현재 시간 넣기
//		//q1 -> entity 생성 완료
//		questionRepository.save(q2);		
//		
//		
//	}
	
	@Test
	@DisplayName("모든 질문글 조회하기 테스트")
	public void testJpa2() {
		List<Question> allQuestion = questionRepository.findAll(); //모든 질문글 조회하기 , findAll은 repository에 따로 만들지 않아도됨
		assertEquals(2, allQuestion.size());  // 예상 결과 확인하기(기대값, 실제값) : 같지 않으면 test 실패(글의 총 갯수)
		
		Question question = allQuestion.get(0); //첫번째 질문글 가져오기
		assertEquals("sbb가 무엇인가요?", question.getSubject());
		
	}
	
	@Test
	@DisplayName("질문 글 번호(기본키인 id)로 조회 테스트")
	public void testJpa3() {
		Optional<Question> qOptional = questionRepository.findById(3); //기본키로 조회 : 1번 글 가져오기 , 기본키 조회는 Optional로 받아줘야한다(null값 방지를 위해)
		if(qOptional.isPresent()) { //참이면 기본키 번호가 존재
			Question question = qOptional.get(); // 글 꺼내기, get 메서드를 사용해서 기본키로 검색한 글 하나를 꺼냄
			assertEquals("sbb가 무엇인가요?", question.getSubject());
		}
	}
	
	
	@Test
	@DisplayName("질문 제목으로 조회한 글의 번호가 3번인지 테스트 ")
	public void testJpa4() {
		 Question question = questionRepository.findBySubject("sbb가 무엇인가요?");
		//select * from question where subject = 'sbb가 무엇인가요?'
		 assertEquals(3, question.getId());
	}
	
	@Test
	@DisplayName("질문 제목과 내용으로 조회한 글의 번호가 3번인지 테스트 ")
	public void testJpa5() {
		 Question question = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?","sbb에 대해 알고 싶습니다");
		//select * from question where subject = 'sbb가 무엇인가요?' and content = 'sbb에 대해 알고 싶습니다'
		 assertEquals(3, question.getId());
	}
	
	@Test
	@DisplayName("질문 제목에 특정 단어가 들어간 레코드를 조회한 글 테스트 ")
	public void testJpa6() {
		 List<Question> questionList = questionRepository.findBySubjectLike("sbb%");
		 Question question = questionList.get(0);  // 첫번째 레코드 가져오기
		 assertEquals("sbb가 무엇인가요?", question.getSubject());
	}
	
	
	
	

}