package kevin.study.redislettuce;

import kevin.study.redislettuce.dto.redis.RedisCrudSaveRequestDto;
import kevin.study.redislettuce.repository.redis.RedisCrudRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedisControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private RedisCrudRepository redisCrudRepository;

    @AfterEach
    public void tearDown() throws Exception {
        redisCrudRepository.deleteAll();
    }

    @Test
    public void basic() {
        //given
        String url = "http://localhost:" + port;

        //when
        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(url, String.class);

        //then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isEqualTo("ok");
    }

    @Test
    public void basicSaveFind() {
        //given
        String url = "http://localhost:" + port + "/save";
        RedisCrudSaveRequestDto requestDto = RedisCrudSaveRequestDto.builder()
                .id(1L)
                .description("description")
                .updatedAt(LocalDateTime.now())
                .build();

        //when
        ResponseEntity<Long> responseEntity = testRestTemplate.postForEntity(url, requestDto, Long.class);

        //then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseEntity.getBody()).isGreaterThan(0L);
    }
}
