package com.kmii.kmiboard.question;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
	//TODO: public Page<Question> findAll(Pageable pageable); 원래 방법
	
	@Query(
		      value = "SELECT * FROM ( " +
		              " SELECT q.*, ROWNUM rnum FROM ( " +
		              "   SELECT * FROM question ORDER BY createdate DESC " +
		              " ) q WHERE ROWNUM <= :endRow " +
		              ") WHERE rnum > :startRow",
		      nativeQuery = true)
	
	    List<Question> findQuestionsWithPaging(@Param("startRow") int startRow,
	                                           @Param("endRow") int endRow);
	
	//검색 결과를 페이징하는 리스트 조회문
		@Query(value = 
			       "SELECT * FROM ( " +
			       "   SELECT q.*, ROWNUM rnum FROM ( " +
			       "       SELECT DISTINCT q.* " +
			       "       FROM question q " +
			       "       LEFT OUTER JOIN siteuser u1 ON q.author_id = u1.id " +
			       "       LEFT OUTER JOIN answer a ON a.question_id = q.id " +
			       "       LEFT OUTER JOIN siteuser u2 ON a.author_id = u2.id " +
			       "       WHERE q.subject LIKE '%' || :kw || '%' " +
			       "          OR q.content LIKE '%' || :kw || '%' " +
			       "          OR u1.username LIKE '%' || :kw || '%' " +
			       "          OR a.content LIKE '%' || :kw || '%'" +
			       "          OR u2.username LIKE '%' || :kw || '%'" +
			       "       ORDER BY q.createdate DESC " +
			       "   ) q WHERE ROWNUM <= :endRow " +
			       ") WHERE rnum > :startRow", 
		         nativeQuery = true)
		
		List<Question> searchQuestionsWithPaging(@Param("kw") String kw , @Param("startRow") int startRow,
		                                           @Param("endRow") int endRow);
		
		
		//검색 결과 총 갯수 반환
			@Query(value =   
				       "       SELECT COUNT(DISTINCT q.id) " +
				       "       FROM question q " +
				       "       LEFT OUTER JOIN siteuser u1 ON q.author_id = u1.id " +
				       "       LEFT OUTER JOIN answer a ON a.question_id = q.id " +
				       "       LEFT OUTER JOIN siteuser u2 ON a.author_id = u2.id " +
				       "       WHERE q.subject LIKE '%' || :kw || '%' " +
				       "          OR q.content LIKE '%' || :kw || '%' " +
				       "          OR u1.username LIKE '%' || :kw || '%' " +
				       "          OR a.content LIKE '%' || :kw || '%' " +
				       "          OR u2.username LIKE '%' || :kw || '%' ",		       
			       nativeQuery = true)
			public int countSearchResult(@Param("kw") String kw);
		
	
	
	 // Page<Question> findAll(Specification<Question> spec, Pageable pageable);
	
	
		
	//	@Query(
	//	value = "UPDATE question SET hit = hit + 1 WHERE id = :id"
	//	, nativeQuery = true)
	//@Query("UPDATE question q SET q.hit = q.hit + 1 WHERE q.id = :id")
	//public void updateHit(@Param("id") Integer id); //질문글의 기본키 번호로 조회수 증가
		
		
}