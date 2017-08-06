package com.sepism.pangu.handler;


import com.sepism.pangu.constant.ErrorCode;
import com.sepism.pangu.exception.InternalException;
import com.sepism.pangu.exception.InvalidInputException;
import com.sepism.pangu.model.handler.Response;
import lombok.extern.log4j.Log4j2;

/**
 * All controllers should use SepHandler to handle the requests. This handler catches all exceptions and wrap them
 * with proper response code to the client. In this way, the client will always know what happened and no extra
 * information will be exposed to the client.
 */
@Log4j2
public abstract class SepHandler {
    public Response handle(String data) {
        try {
            return process(data);
        } catch (InvalidInputException e) {
            log.warn("The request from the user contains invalid values: ", e);
            return new Response(ErrorCode.INVALID_VALUE, e.getMessage());
        } catch (InternalException e) {
            log.error("The server side error occurs when handling the request: ", e);
            return new Response(ErrorCode.INTERNAL_ERROR, e.getCause().getClass().getName());
        } catch (Exception e) {
            log.error("Unhandled Exception occured when handling the request: ", e);
            return new Response(ErrorCode.INTERNAL_ERROR, e.getCause().getClass().getName());
        }
    }

    protected abstract Response process(String data) throws InvalidInputException, InternalException;
}
