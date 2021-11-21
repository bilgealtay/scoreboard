package com.ravensoftware.scoreboard.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by bilga on 20-11-2021
 */
public class ScoreBoardResponse implements Serializable {

    private Boolean success;
    private Object responseObject;
    private String errorMessage;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Object getResponseObject() {
        return responseObject;
    }

    public void setResponseObject(Object responseObject) {
        this.responseObject = responseObject;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScoreBoardResponse that = (ScoreBoardResponse) o;
        return Objects.equals(success, that.success) &&
                Objects.equals(responseObject, that.responseObject) &&
                Objects.equals(errorMessage, that.errorMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, responseObject, errorMessage);
    }
}
