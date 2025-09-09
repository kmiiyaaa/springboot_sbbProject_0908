package com.kmii.kmiboard.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserCreateForm {
	
	
	@Size(min=3, max=25, message="사용자 ID 길이는 3자 이상 25자 이하 입니다.") // 아이디 길이 3~25자 제한
	@NotEmpty(message="사용자 ID는 필수 입니다")
	private String username;
	
	@NotEmpty(message="사용자 비밀번호는 필수 입니다")
	private String password1;
	
	@NotEmpty(message="사용자 비밀번호 확인은 필수 입니다")
	private String password2;
	
	@NotEmpty(message="사용자 이메일은 필수 입니다")
	@Email  // 이메일 형식 아니면 에러
	private String email;
	

}
