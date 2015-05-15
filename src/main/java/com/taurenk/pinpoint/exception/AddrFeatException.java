package com.taurenk.pinpoint.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by taurenk on 5/15/15.
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND,
        reason = "{'status': 'error', 'message': 'AddrFeat not found' }")  // 404
public class AddrFeatException extends RuntimeException {
}
