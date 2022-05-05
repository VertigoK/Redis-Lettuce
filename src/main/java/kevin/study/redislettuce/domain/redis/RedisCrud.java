package kevin.study.redislettuce.domain.redis;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@RedisHash("redisCrud")
public class RedisCrud {

    @Id
    private Long id;
    private String description;
    private LocalDateTime updatedAt;

    public void update(String description, LocalDateTime updatedAt) {
        if (updatedAt.isAfter(this.updatedAt)) {
            this.description = description;
            this.updatedAt = updatedAt;
        }
    }
}
