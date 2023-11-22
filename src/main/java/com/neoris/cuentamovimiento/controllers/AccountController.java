package com.neoris.cuentamovimiento.controllers;

import com.neoris.cuentamovimiento.exceptions.CuentaMovimientoException;
import com.neoris.cuentamovimiento.services.AccountService;
import com.neoris.cuentamovimiento.vos.AccountRequestVo;
import com.neoris.cuentamovimiento.vos.AccountResponseVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("accounts")
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<Flux<AccountResponseVo>> get(){
        try {
            return ResponseEntity.ok(accountService.findAll());
        } catch (Exception ex) {
            log.error("***ERROR AccountController.get", ex);
            throw new CuentaMovimientoException("Ocurrio un error inesperado al obtener cuentas", ex);
        }
    }

    @PostMapping
    public ResponseEntity<Mono<AccountResponseVo>> post(
            @RequestBody @Validated AccountRequestVo accountRequestDtoMono
    ){
        try {
            return ResponseEntity.ok(accountService.save(accountRequestDtoMono));
        } catch (Exception ex) {
            log.error("***ERROR AccountController.post", ex);
            throw new CuentaMovimientoException("Ocurrio un error inesperado al guardar la cuenta", ex);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Mono<AccountResponseVo>> put(
            @PathVariable("id") UUID id, @RequestBody AccountRequestVo accountRequestDtoMono
    ){
        try {
            return ResponseEntity.ok(accountService.update(id, accountRequestDtoMono));
        } catch (Exception ex) {
            log.error("***ERROR AccountController.put", ex);
            throw new CuentaMovimientoException("Ocurrio un error inesperado al actualizar la cuenta", ex);
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<Mono<AccountResponseVo>> patch(
            @PathVariable("id") UUID id, @RequestBody AccountRequestVo accountRequestDtoMono
    ){
        try {
            return ResponseEntity.ok(accountService.patch(id, accountRequestDtoMono));
        } catch (Exception ex) {
            log.error("***ERROR AccountController.patch", ex);
            throw new CuentaMovimientoException("Ocurrio un error inesperado al actualizar parcialmente la cuenta", ex);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Mono<Void>> delete(@PathVariable("id") UUID id){
        try {
            return ResponseEntity.ok(accountService.delete(id));
        } catch (Exception ex) {
            log.error("***ERROR AccountController.delete", ex);
            throw new CuentaMovimientoException("Ocurrio un error inesperado al eliminar la cuenta", ex);
        }
    }
}
