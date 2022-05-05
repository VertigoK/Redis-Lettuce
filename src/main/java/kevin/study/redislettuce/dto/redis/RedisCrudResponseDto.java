package kevin.study.redislettuce.dto.redis;

import kevin.study.redislettuce.domain.redis.RedisCrud;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RedisCrudResponseDto {

    private final Long id;
    private final String description;
    private final LocalDateTime updatedAt;

    public RedisCrudResponseDto(RedisCrud redisHash) {
        this.id = redisHash.getId();
        this.description = redisHash.getDescription();
        this.updatedAt = redisHash.getUpdatedAt();
    }
}
