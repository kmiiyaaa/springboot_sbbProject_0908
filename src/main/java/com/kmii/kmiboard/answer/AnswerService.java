package com.kmii.kmiboard.answer;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		
}
