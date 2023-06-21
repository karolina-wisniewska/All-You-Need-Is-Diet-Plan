package pl.coderslab;

import org.kurento.client.KurentoClient;
import org.kurento.client.Properties;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import pl.coderslab.allyouneedisdietplan.external.one2onecall.CallHandler;
import pl.coderslab.allyouneedisdietplan.external.one2onecall.UserRegistry;

@SpringBootApplication
@ComponentScan(basePackages = {"pl.coderslab.allyouneedisdietplan"})
@EnableWebSocket
public class AllYouNeedIsDietPlanApplication implements WebSocketConfigurer {

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
  @Bean
  public CallHandler callHandler() {
    return new CallHandler();
  }

  @Bean
  public UserRegistry registry() {
    return new UserRegistry();
  }

  @Bean
  public Properties properties(){
    return new Properties();
  }

  @Bean
  public KurentoClient kurentoClient() {
    return KurentoClient.create("ws://kurento:8888/kurento");
  }

  @Bean
  public ServletServerContainerFactoryBean createServletServerContainerFactoryBean() {
    ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
    container.setMaxTextMessageBufferSize(32768);
    return container;
  }

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(callHandler(), "/call");
  }
  public static void main(String[] args) {
    SpringApplication.run(AllYouNeedIsDietPlanApplication.class, args);
  }
}
