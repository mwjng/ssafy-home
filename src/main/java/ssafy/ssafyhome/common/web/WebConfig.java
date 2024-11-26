package ssafy.ssafyhome.common.web;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

//    private final TaskExecutor taskExecutor;
//
//    @Override
//    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
//        configurer.setTaskExecutor(taskExecutor);
//    }
//
//    public AsyncTaskExecutor mvcTaskExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(10); // 기본적으로 유지할 스레드 수
//        executor.setMaxPoolSize(50);  // 최대 스레드 수
//        executor.setQueueCapacity(100); // 큐에 보관할 요청 수
//        executor.setThreadNamePrefix("mvc-task-");
//        executor.initialize();
//        return executor;
//    }
}
