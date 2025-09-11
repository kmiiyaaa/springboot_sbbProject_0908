package com.kmii.kmiboard.question;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
	
	public Question findBySubject(String subject);  //subject -> 테이블에 존재해야하는 필드 이름
	//SELECT * FROM QUESTION WHERE Subject=?
	
	public Question findBySubjectAndContent(String subject, String content);
	//SELECT * FROM QUESTION WHERE Subject=? and Content=?
	
	public List<Question> findBySubjectLike(String keyword);  // 제목에 특정 단어가 포함된 레코드 반환
	//SELECT * FROM QUESTION WHERE Subject Like %?%
	
	
	
	
	//페이징 관련
	//TODO: public Page<Question> findAll(Pageable pageable);
	@Query(
	         value = "SELECT * FROM ( " +
	                 " SELECT q.*, ROWNUM rnum FROM ( " +
	                 "   SELECT * FROM question ORDER BY createdate DESC " +
	                 " ) q WHERE ROWNUM <= :endRow " +
	                 ") WHERE rnum > :startRow",
	         nativeQuery = true)
	List<Question> findQuestionsWithPaging(@Param("startRow") int startRow,
	                                           @Param("endRow") int endRow);
	
	
		
	//	@Query(
	//	value = "UPDATE question SET hit = hit + 1 WHERE id = :id"
	//	, nativeQuery = true)
	//@Query("UPDATE question q SET q.hit = q.hit + 1 WHERE q.id = :id")
	//public void updateHit(@Param("id") Integer id); //질문글의 기본키 번호로 조회수 증가
		
		
}