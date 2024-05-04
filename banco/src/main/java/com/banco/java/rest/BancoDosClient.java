package com.banco.java.rest;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "banco-dos", url = "")
public interface BancoDosClient {
}
