package com.kosa.gallerygather.controller;

import com.kosa.gallerygather.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestAuthenticationController {

    @GetMapping("/test/canaccess/logined")
    public String canAccessAuthenticatedMember(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 서버에서 로그인된 회원 정보를 조회하고자 할 때, 사용해야하는 어노테이션

        /*
            근데, 서버에서 로그인했는지 여부를 확인하기 위해서는 클라이언트에서 인증 관련 정보를 함께 전송해야한다.

            클라이언트 요청에서 axios를 사용할 때 Authentication 헤더에 jwt 토큰 값을 넘겨주어야 한다.
            axios.get("http://localhost:8080/test/canaccess/logined", {
                headers:{
                    Authorization: localStorage.getItem('accessToken'),
                },
            })

            요청 헤더에 Authorization 헤더를 포함하여 메시지를 전송해야한다.
         */
        return userDetails.getEmail() + "ok";
    }
}



// ~/IdeaProject/gallerygather/gallerygather/gallerygather