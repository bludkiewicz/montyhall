package com.bludkiewicz.montyhall;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MontyhallApplicationTests {

	/**
	 * Oh the things we do for 100% code coverage.
	 * This test requires that port 8080 be available.
	 */
	@Test
	void contextLoads() {
		MontyhallApplication.main(new String[]{});
	}

}
