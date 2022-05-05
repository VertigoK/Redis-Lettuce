package kevin.study.redislettuce.service.redis;

import kevin.study.redislettuce.domain.redis.RedisCrud;
import kevin.study.redislettuce.dto.redis.RedisCrudResponseDto;
import kevin.study.redislettuce.dto.redis.RedisCrudSaveRequestDto;
import kevin.study.redislettuce.repository.redis.RedisCrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RedisCrudService {

    private final RedisCrudRepository redisCrudRepository;

    @Transactional
    public Long save(RedisCrudSaveRequestDto requestDto) {
        return redisCrudRepository.save(requestDto.toRedisHash()).getId();
    }

    public RedisCrudResponseDto get(Long id) {
        RedisCrud redisCrud = redisCrudRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Nothing saved. id=" + id));
        return new RedisCrudResponseDto(redisCrud);
    }
}
