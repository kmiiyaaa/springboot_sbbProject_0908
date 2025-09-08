package com.kmii.kmiboard.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


@Entity
@Table(name = "answer") //실제로 매핑될 데이터베이스의 테이블 이름 설정
@SequenceGenerator(
		name = "ANSWER_SEQ_GENERATOR", //JPA 내부 시퀀스 이름
		sequenceName = "ANSWER_SEQ", //실제 DB 시퀀스 이름 
		initialValue = 1, //시퀀스 시작값
		allocationSize = 1 //시퀀스 증가치
		)
public class Answer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ANSWER_SEQ_GENERATOR")
	private Integer id; //기본키, 자동증가
	
	@Column(length = 500)
	private String content;
	
	
	private LocalDateTime createdate;
	
	//N:1 -> 답변들: 질문 ->@ManyToOne
	@ManyToOne
	private Question question;  // 질문 하나 
	
	

}




