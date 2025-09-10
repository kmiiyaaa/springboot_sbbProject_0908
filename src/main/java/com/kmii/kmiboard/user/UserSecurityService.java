package com.kmii.kmiboard.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kmii.kmiboard.UserRole;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {
	
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //UserDetails - 사용자 정보 담는 객체/ 인터페이스

		Optional<SiteUser> _siteUser = userRepository.findByUsername(username);
		
		if(_siteUser.isEmpty()) {
			throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
		
		}
		
		SiteUser siteUser = _siteUser.get();  // 아이디로 찾은 레코드
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		//사용자의 권한 정보를 나타내는 GrantedAuthority 객체들의 리스트(담을 리스트)
				
		if("admin".equals(username)) {  // 참이면 admin권한으로 설정
			authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
		}else {
			authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
		}
		
		
		return new User(siteUser.getUsername(),siteUser.getPassword(),authorities);
	}
	
	

	

}