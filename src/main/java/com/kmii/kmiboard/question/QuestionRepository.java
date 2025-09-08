package com.kmii.kmiboard.question;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
	
	public Question findBySubject(String subject);  //subject -> 테이블에 존재해야하는 필드 이름
	//SELECT * FROM QUESTION WHERE Subject=?
	
	public Question findBySubjectAndContent(String subject, String content);
	//SELECT * FROM QUESTION WHERE Subject=? and Content=?
	
	public List<Question> findBySubjectLike(String keyword);  // 제목에 특정 단어가 포함된 레코드 반환
	//SELECT * FROM QUESTION WHERE Subject Like %?%
}