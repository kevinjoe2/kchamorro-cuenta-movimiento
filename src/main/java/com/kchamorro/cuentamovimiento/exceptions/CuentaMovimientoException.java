package com.kchamorro.cuentamovimiento.exceptions;

import lombok.Builder;

public class CuentaMovimientoException extends RuntimeException{

    @Builder(builderMethodName = "message")
    public CuentaMovimientoException(String message) {
        super(message);
    }

    @Builder(builderMethodName = "throwable")
    public CuentaMovimientoException(String message, Throwable cause) {
        super(message, cause);
    }
}
