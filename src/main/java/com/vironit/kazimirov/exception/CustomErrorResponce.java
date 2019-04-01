package com.vironit.kazimirov.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomErrorResponce {
    private LocalDateTime timeStamp;
    private int status;
    private String message;

}
