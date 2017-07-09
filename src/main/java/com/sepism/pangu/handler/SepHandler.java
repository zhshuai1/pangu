package com.sepism.pangu.handler;


import com.sepism.pangu.constant.ErrorCode;
import com.sepism.pangu.exception.InternalException;
import com.sepism.pangu.exception.InvalidInputException;
import com.sepism.pangu.model.handler.Request;
import com.sepism.pangu.model.handler.Response;
import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class SepHandler {
    public Response handle(Request request) {
        try {
            return process(request);
        } catch (InvalidInputException e) {
            log.warn("The request from the user contains invalid values: ", e);
            return new Response(ErrorCode.INVALID_VALUE, e.getMessage());
        } catch (InternalException e) {
            log.info("The server side error occurs when handling the request: ", e);
            return new Response(ErrorCode.INTERNAL_ERROR);
        } catch (Exception e) {
            log.info("Unhandled Exception occured when handling the request: ", e);
            return new Response(ErrorCode.INTERNAL_ERROR);
        }
    }

    public abstract Response process(Request request) throws InvalidInputException, InternalException;
}
