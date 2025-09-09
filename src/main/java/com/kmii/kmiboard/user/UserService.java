package com.kmii.kmiboard.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//유저 등록 서비스
	public SiteUser create(String username, String email, String password) {
		SiteUser user = new SiteUser();
		user.setUsername(username);
		user.setEmail(email);
	//	BCryptPasswordEncoder passwordEncoder  = new BCryptPasswordEncoder();  이렇게 사용하지 않고 공통 부분으로 빼고 autowired로 써주면 유지보수에 좋다
		String cryptPassword = passwordEncoder.encode(password);
		user.setPassword(cryptPassword);  // 비밀번호 암호화한 후 암호문을 DB에 입력
		userRepository.save(user);
		
		return user;
	}
	

}
