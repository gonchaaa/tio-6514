package com.self.esteem.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="demo", url="${demo.service.url}")
public interface DemoClient {
}
