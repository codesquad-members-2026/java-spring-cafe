package com.codesquad.cafe;


import com.codesquad.cafe.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CafeApplicationTests {

	@Test
	@DisplayName("회원가입 시 입력한 정보가 객체에 정확히 저장되어야 한다")
	void createUser() {
		String userId =("stojum");
		String name = "송원재";

		User user = new User();
		user.setUserId(userId);
		user.setName(name);

		assertEquals(userId, user.getUserId());
		assertEquals(name, user.getName());
	}

}
