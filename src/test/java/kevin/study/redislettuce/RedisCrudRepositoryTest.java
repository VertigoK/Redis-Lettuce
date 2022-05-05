package kevin.study.redislettuce;

import kevin.study.redislettuce.domain.redis.RedisCrud;
import kevin.study.redislettuce.repository.redis.RedisCrudRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@SpringBootTest
public class RedisCrudRepositoryTest {

    @Autowired
    private RedisCrudRepository redisCrudRepository;

    @AfterEach
    public void tearDown() throws Exception {
        redisCrudRepository.deleteAll();
    }

    @Test
    public void basicSaveFind() {
        //given
        Long id = 0L;
        String description = "description";
        LocalDateTime updatedAt = LocalDateTime.of(2022, 4, 28, 5, 15);

        RedisCrud redisCrudSave = RedisCrud.builder()
                .id(id)
                .description(description)
                .updatedAt(updatedAt)
                .build();

        //when
        redisCrudRepository.save(redisCrudSave);

        //then
        RedisCrud redisCrudFind = redisCrudRepository.findById(id).orElseThrow(NoSuchElementException::new);
        Assertions.assertThat(redisCrudFind.getDescription()).isEqualTo("description");
        Assertions.assertThat(redisCrudFind.getUpdatedAt()).isEqualTo(updatedAt);
    }

    @Test
    public void basicSaveUpdate() {
        //given
        Long id = 0L;
        String description = "description";
        LocalDateTime updatedAt = LocalDateTime.of(2022, 4, 28, 5, 15);

        RedisCrud redisCrudSave = RedisCrud.builder()
                .id(id)
                .description(description)
                .updatedAt(updatedAt)
                .build();

        redisCrudRepository.save(redisCrudSave);

        //when
        RedisCrud redisCrudUpdate = redisCrudRepository.findById(id).orElseThrow(NoSuchElementException::new);
        redisCrudUpdate.update("updated description", LocalDateTime.of(2022, 4, 29, 5, 15));
        redisCrudRepository.save(redisCrudUpdate);

        //then
        RedisCrud redisCrudFind = redisCrudRepository.findById(id).orElseThrow(NoSuchElementException::new);
        Assertions.assertThat(redisCrudFind.getDescription()).isEqualTo("updated description");
    }
}
