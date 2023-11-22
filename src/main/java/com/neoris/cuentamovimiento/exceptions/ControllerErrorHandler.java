package com.neoris.cuentamovimiento.exceptions;


import com.neoris.cuentamovimiento.vos.ErrorVo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.*;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class ControllerErrorHandler extends ResponseEntityExceptionHandler {

    @Override
    protected Mono<ResponseEntity<Object>> handleExceptionInternal(
            Exception ex, Object body, HttpHeaders headers, HttpStatusCode status, ServerWebExchange exchange
    ) {
        return super.handleExceptionInternal(ex,
                ErrorVo.builder().status(HttpStatus.BAD_REQUEST).error(ex.getCause().getMessage()).build(),
                headers, status, exchange);
    }
}
