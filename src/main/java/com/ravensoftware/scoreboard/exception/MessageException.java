package com.ravensoftware.scoreboard.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by bilga on 20-11-2021
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class MessageException extends RuntimeException {

    public MessageException() {
    }

    public MessageException(String message) {
        super(message);
    }


}
