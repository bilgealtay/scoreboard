package com.ravensoftware.scoreboard.base;

import com.ravensoftware.scoreboard.exception.EntityNotFoundException;
import com.ravensoftware.scoreboard.exception.MessageException;

/**
 * Created by bilga on 20-11-2021
 */
public class BaseControl {

    public void throwMessageException(String message) {
        throw new MessageException(message);
    }

    public void throwEntityNotFoundException(String message) {
        throw new EntityNotFoundException(message);
    }

}
