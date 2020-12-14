package company.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class CompanyRepositoryTest {

  @Autowired
  private CompanyRepository companyRepository;

  @BeforeEach
  void setUp() {
  }

  @AfterEach
  void tearDown() {
  }
//
//  @Test
//  void
}