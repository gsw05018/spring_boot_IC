package com.sbs.sbb.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SiteUser> _siteUser = this.userRepository.findByusername(username);
        // 주어진 사용자 이름에 해당하는 SiteUser를 찾음
        if (_siteUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }
        // 존재하지 않을 시 오류 발생
        SiteUser siteUser = _siteUser.get();
        // 존재할 시 SiteUser를 가져옴
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 사용자 권한을 담는 리스트 authorities 생성
        if ("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        // username에 따른 권한 부여
        return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
        // UserDetails 객체를 생성하여 반환
    }
}
