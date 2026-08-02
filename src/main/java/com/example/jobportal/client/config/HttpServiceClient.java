package com.example.jobportal.client.config;

import com.example.jobportal.client.service.TodoService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.service.registry.ImportHttpServices;

@Configuration
@ImportHttpServices(types={TodoService.class})
public class HttpServiceClient {
}
