package com.banco.java.rest;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "banco-uno", url = "")
public interface BancoUnoClient {
}
