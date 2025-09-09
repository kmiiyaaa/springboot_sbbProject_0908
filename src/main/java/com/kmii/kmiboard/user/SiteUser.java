package com.kmii.kmiboard.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "siteuser") //실제로 매핑될 데이터베이스의 테이블 이름 설정
@SequenceGenerator(
		name = "USER_SEQ_GENERATOR", //JPA 내부 시퀀스 이름
		sequenceName = "USER_SEQ", //실제 DB 시퀀스 이름 
		initialValue = 1, //시퀀스 시작값
		allocationSize = 1 //시퀀스 증가치
		)
public class SiteUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ_GENERATOR")
	private Long id;  // 유저번호, 기본키
	
	@Column(unique = true) //중복불가
	private String username;  //유저 아이디
	
	private String password;  // 유저 비밀번호
	
	@Column(unique = true)// 중복불가
	private String email; // 유저 이메일

}
