package cn.edu.zucc.g4;

import cn.edu.zucc.g4.entity.User;
import cn.edu.zucc.g4.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MedicineHelperBackendApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void testGetAll() {
        User user = userMapper.selectById(1);
        System.out.println(user);
    }

}
