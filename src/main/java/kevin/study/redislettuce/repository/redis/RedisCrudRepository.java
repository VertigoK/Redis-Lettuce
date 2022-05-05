package kevin.study.redislettuce.repository.redis;

import kevin.study.redislettuce.domain.redis.RedisCrud;
import org.springframework.data.repository.CrudRepository;

public interface RedisCrudRepository extends CrudRepository<RedisCrud, Long> {

}
