package stomp.demo.config.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;

/**
 * WebSocketStompConfig
 */
@Configuration
@ComponentScan(basePackages = {"stomp.demo"})
@EnableWebSocketMessageBroker
public class WebSocketStompConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 添加一个/stomp_server端点，客户端就可以通过这个端点来进行连接；withSockJS作用是添加SockJS支持
        registry.addEndpoint("/stomp_server").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {

        config.enableStompBrokerRelay("/topic", "/queue")
                .setRelayHost("localhost")
                .setRelayPort(61613)
                .setClientLogin("root")
                .setClientPasscode("root123");

        //config.enableSimpleBroker("/topic", "/queue");

        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/user");

        // config.configureBrokerChannel().setInterceptors(config.configureBrokerChannel().setInterceptors(applicationContext.getBean(WebSocketSecurityInterceptor.class));
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(applicationContext.getBean(WebSocketInterceptor.class));
    }

    @Override
	public void configureWebSocketTransport(WebSocketTransportRegistration registration) {

        registration.addDecoratorFactory(CustomWebSocketHandlerDecorator::new);

	}

}