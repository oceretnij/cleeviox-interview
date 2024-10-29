package com.cleeviox.interview.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Configuration
public class Web3jConfig {

    @Bean
    public Web3j web3j() {
        return Web3j.build(new HttpService("https://mainnet.infura.io/v3/97d9de3bf3b54c7d8ffa2f63f4238389"));
    }

}
