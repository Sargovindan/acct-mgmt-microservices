package com.account.management.accounts.repositories;

import com.account.management.accounts.TestConfig;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@Import(TestConfig.class)
@TestPropertySource(locations = "classpath:application-dev.properties")
public class RepoTest {

}
