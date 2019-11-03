package org.muses.jeeplatform;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.muses.jeeplatform.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@org.springframework.boot.test.context.SpringBootTest
public class SpringBootTest {

    @Autowired
    User user;

    @Test
    public void testConfigurationProperties(){
        System.out.println(user);
    }

}
