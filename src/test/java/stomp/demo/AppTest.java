package stomp.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import stomp.demo.config.web.RootConfig;
import stomp.demo.config.web.WebConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootConfig.class, WebConfig.class })
@WebAppConfiguration
/**
 * Unit test for simple App.
 */
public class AppTest {

    @Autowired
    WebApplicationContext ctx;


    @Test
    public void print() {
        String[] beanNames = ctx.getBeanDefinitionNames();
        for(String bean: beanNames) {
            System.out.println(bean);
        }

        //send.sendMessage(new HelloMessage("server"));
    }

}
