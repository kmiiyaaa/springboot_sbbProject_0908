package com.kmii.kmiboard.answer;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kmii.kmiboard.DataNotFoundException;
import com.kmii.kmiboard.question.Question;
import com.kmii.kmiboard.user.SiteUser;

@Service
public class AnswerService {

		@Autowired
		private AnswerRepository answerRepository;
		
		public void create(Question question ,String content, SiteUser author) { 
			Answer answer = new Answer();
			answer.setContent(content);
			answer.setCreatedate(LocalDateTime.now()); //  현재 시간 등록
			answer.setQuestion(question);
			answer.setAuthor(author);
			answerRepository.save(answer); 
			
			
		}
		
		public Answer getAnswer(Integer id) { //기본키인 답변 id를 인수로 넣어주면 해당 엔티티 반환
			Optional<Answer> _answer = answerRepository.findById(id); //기본키로 엔티티 조회하기
			
			if(_answer.isPresent()) {
				return _answer.get(); //->해당 answer 엔티티가 올바르게 반환
			} else {
				throw new DataNotFoundException("해당 답변이 존재하지 않습니다.");
			}
		}
		
		public void modify(Answer answer, String content) { //답변 수정하기
			answer.setContent(content);
			answer.setModifydate(LocalDateTime.now()); //답변 수정한 일시
			answerRepository.save(answer);
		}
		
		public void delete(Answer answer) { //답변 삭제하기
			answerRepository.delete(answer);
		}
		
		public void vote(Answer answer, SiteUser siteUser) { //->update문으로 만들어줘야 함
			answer.getVoter().add(siteUser);
			//answer->추천을 받은 글의 번호로 조회한 답변 엔티티
			//answer의 멤버인 voter를 get해서 voter에 추천을 누른 유저의 엔티티를 추가해 줌
			answerRepository.save(answer); //추천한 유저수가 변경된 답변 엔티티를 다시 save해서 갱신
		}
		
}
