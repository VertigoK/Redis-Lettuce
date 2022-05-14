package kevin.study.redislettuce;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SpringBootTest
public class RedisRepositoryConfigTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //String
    @Test
    @DisplayName("Test for String")
    public void testString() {
        final String key = "testString";
        //opsForValue: Serialize/Deserialize a String
        final ValueOperations<String, String> stringStringValueOperations = stringRedisTemplate.opsForValue();

        stringStringValueOperations.set(key, "1");
        final String result_1 = stringStringValueOperations.get(key);
        System.out.println("result_1 = " + result_1);

        stringStringValueOperations.increment(key);
        final String result_2 = stringStringValueOperations.get(key);
        System.out.println("result_2 = " + result_2);

        //redis-cli command:
        //get testString
    }

    //List
    @Test
    @DisplayName("Test for List")
    public void testList() {
        final String key = "testList";
        //opsForList: Serialize/Deserialize a List
        final ListOperations<String, String> stringStringListOperations = stringRedisTemplate.opsForList();

        stringStringListOperations.rightPush(key, "H");
        stringStringListOperations.rightPush(key, "E");
        stringStringListOperations.rightPush(key, "L");
        stringStringListOperations.rightPush(key, "L");
        stringStringListOperations.rightPush(key, "O");

        stringStringListOperations.rightPushAll(key, " ", "W", "O", "R", "L", "D");

        final String character_1 = stringStringListOperations.index(key, 1);
        System.out.println("character_1 = " + character_1);

        final Long size = stringStringListOperations.size(key);
        System.out.println("size = " + size);

        final List<String> resultRange = stringStringListOperations.range(key, 0, 10);
        assert resultRange != null;
        System.out.println("resultRange = " + Arrays.toString(resultRange.toArray()));

        //redis-cli command:
        //lindex testList 1
        //lrange testList 0 10
    }

    //Set
    @Test
    @DisplayName("Test for Set")
    public void testSet() {
        final String key = "testSet";
        //opsForSet: Serialize/Deserialize a Set
        SetOperations<String, String> stringStringSetOperations = stringRedisTemplate.opsForSet();

        stringStringSetOperations.add(key, "H");
        stringStringSetOperations.add(key, "E");
        stringStringSetOperations.add(key, "L");
        stringStringSetOperations.add(key, "L");
        stringStringSetOperations.add(key, "O");

        Set<String> test = stringStringSetOperations.members(key);
        assert test != null;
        System.out.println("members = " + Arrays.toString(test.toArray()));

        final Long size = stringStringSetOperations.size(key);
        System.out.println("size = " + size);

        Cursor<String> cursor = stringStringSetOperations.scan(key,
                ScanOptions.scanOptions().match("*").count(3).build());
        while (cursor.hasNext()) {
            System.out.println("cursor = " + cursor.next());
        }

        //redis-cli command:
        //smembers testSet
    }

    //Sorted Set
    @Test
    @DisplayName("Test for Sorted Set")
    public void testSortedSet() {
        final String key = "testSortedSet";
        //opsForZSet: Serialize/Deserialize a Sorted Set
        ZSetOperations<String, String> stringStringZSetOperations = stringRedisTemplate.opsForZSet();

        stringStringZSetOperations.add(key, "H", 1);
        stringStringZSetOperations.add(key, "E", 5);
        stringStringZSetOperations.add(key, "L", 10);
        stringStringZSetOperations.add(key, "L", 15);
        stringStringZSetOperations.add(key, "O", 20);

        Set<String> range = stringStringZSetOperations.range(key, 0, 4);
        assert range != null;
        System.out.println("range = " + Arrays.toString(range.toArray()));

        final Long size = stringStringZSetOperations.size(key);
        System.out.println("size = " + size);

        Set<String> scoreRange = stringStringZSetOperations.rangeByScore(key, 0, 13);
        assert scoreRange != null;
        System.out.println("scoreRange = " + Arrays.toString(scoreRange.toArray()));

        //redis-cli command:
        //zrange testSortedSet 0 4
        //zrange testSortedSet 0 13 byscore
    }

    //Hash
    @Test
    @DisplayName("Test for Hash")
    public void testHash() {
        final String key = "testHash";
        //opsForHash: Serialize/Deserialize a Hash
        HashOperations<String, Object, Object> stringObjectObjectHashOperations = stringRedisTemplate.opsForHash();

        stringObjectObjectHashOperations.put(key, "Hello", "testHash");
        stringObjectObjectHashOperations.put(key, "Hello2", "testHash2");
        stringObjectObjectHashOperations.put(key, "Hello3", "testHash3");

        Object hello = stringObjectObjectHashOperations.get(key, "Hello");
        System.out.println("hello = " + hello);

        Map<Object, Object> entries = stringObjectObjectHashOperations.entries(key);
        System.out.println("entries = " + entries.get("Hello2"));

        Long size = stringObjectObjectHashOperations.size(key);
        System.out.println("size = " + size);

        //redis-cli command:
        //hget testHash Hello2
    }

    //redis-cli command to delete all the keys:
    //flushall
}
