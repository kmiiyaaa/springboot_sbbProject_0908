package com.kmii.kmiboard.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kmii.kmiboard.DataNotFoundException;
import com.kmii.kmiboard.user.SiteUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuestionService {
	
	//@Autowired
	private final QuestionRepository questionRepository;  
	//@RequiredArgsConstructor 이용해서 생성자 방식으로 주입된 questionRepository - final 필드만 가능
	
	public List<Question> getList() {  //모든 질문글 가져오기
		
		// Pageable pageable = PageRequest.of(page, 10);  // 한페이지당 10개의 게시글 표시
		
		return questionRepository.findAll();
		
	}
	
	public Question getQuestion(Integer id) {  //기본키인 질문 글 번호로 질문 1개 가져오기
		Optional<Question> qOptional = questionRepository.findById(id);
		
		if(qOptional.isPresent()) {
			return qOptional.get();  // question 반환
		} else {
			throw new DataNotFoundException(null);   //에러 페이지 출력
		}
		
	}
	
	
	public void create(String subject, String content, SiteUser user) {
		Question question = new Question();
		question.setSubject(subject);
		question.setContent(content);
		question.setCreatedate(LocalDateTime.now());
		question.setAuthor(user);
		questionRepository.save(question);
		
	}
	

}
