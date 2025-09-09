package com.kmii.kmiboard.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kmii.kmiboard.DataNotFoundException;

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
	
	//유저 ID로 엔티티 가져오기
	public SiteUser getUser(String username) {
		Optional<SiteUser> _siteUser = userRepository.findByUsername(username);
		
		if(_siteUser.isPresent()) {
			SiteUser siteUser = _siteUser.get();
			return siteUser;
		} else {
			throw new DataNotFoundException("해당 유저는 존재하지 않는 유저입니다");
		}
		
		
	}
		

}
