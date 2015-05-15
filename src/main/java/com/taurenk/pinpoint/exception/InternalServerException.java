package com.taurenk.pinpoint.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by taurenk on 5/15/15.
 */
@ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR,
        reason = "{'status': 'error', 'message': 'An unknown error has occurred.' }")  // 500
public class InternalServerException extends RuntimeException {
}
