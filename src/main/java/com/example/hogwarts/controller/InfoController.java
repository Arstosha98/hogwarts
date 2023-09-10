package com.example.hogwarts.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.LongStream;
import java.util.stream.Stream;
@RestController
@RequestMapping("/properties")
public class InfoController {

    @Value("${server.port:-1}")
    private int port;

    @GetMapping("/port")
    public int getPort(){
        return port;
    }

    @GetMapping("/sum")
    public String calculateSum(){
        // Изначально long sum = Stream.iterate(1, a -> a + 1)
        // .limit(50_000_000)
        // .reduce(0l, (a, b) -> a + b );
        long startTime = System.currentTimeMillis();
        long result = LongStream.range(1,50_000_000)
                .parallel()
                .sum();
        long timeConsumed = System.currentTimeMillis() - startTime;
        return "Time consumed = " + timeConsumed + " ms.Result = " + result;
    }
}