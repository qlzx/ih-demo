import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author lh0
 * @date 2021/11/23
 * @desc
 */
public class Solution1 {

    //int i = 1100000000;

    LruCache<Integer, User> lruCache = new LruCache<>(2000000);

    private final UserHashRepository userHashRepository;

    public Solution1(
        UserHashRepository userHashRepository) {this.userHashRepository = userHashRepository;}

    public User findUser(int hash) {
        // 数据库11亿数据
        User user = lruCache.get(hash);
        if (user == null) {
            user = loadDBByHash(hash);
        }
        return user;
    }

    private User loadDBByHash(int hash) {
        return Optional.ofNullable(userHashRepository.get(hash))
            .map(UserHash::getUser)
            .orElse(null);
    }

    public static void main(String[] args) {
        UserHashRepository userHashRepository = new UserHashRepository();
        // 初始化hash-用户映射关系表
        userHashRepository.init();
        // 除了新加映射表，实在想不到还有什么方式能够根据hash就能匹配到对应用户了。。
        Solution1 solution1 = new Solution1(userHashRepository);

        User user = solution1.findUser(12321);
        System.out.println(user);
    }

    @Data
    static class User {
        private Long id;

        /**
         * 注册手机号
         */
        private String mobile;

        /**
         * 注册名称
         */
        private String name;
    }

    /**
     * hash-用户映射表
     */
    @Data
    @AllArgsConstructor
    static class UserHash {
        private int hash;

        private User user;
    }

    static class UserHashRepository {
        LruCache<Integer, UserHash> table = new LruCache<>(1100000000);

        UserHash get(int hash) {
            return table.get(hash);
        }

        void init() {
            // 遍历用户表 构建出hash用户映射表
            List<User> allUsers = new ArrayList<>();
            allUsers.forEach(item -> {
                int hash = item.getMobile().hashCode();
                this.table.put(hash, new UserHash(hash, item));
            });
        }

        void consumeMobileUpdate(){
            // 订阅手机号变更消息
        }
    }

    static class LruCache<K, V> extends LinkedHashMap<K, V> {

        private final int maxSize;

        public LruCache(int maxSize) {
            this.maxSize = maxSize;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry eldest) {
            return this.size() > maxSize;
        }
    }
}
