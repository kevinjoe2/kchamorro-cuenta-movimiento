package com.neoris.cuentamovimiento.controllers;

import com.neoris.cuentamovimiento.services.AccountService;
import com.neoris.cuentamovimiento.vos.AccountRequestVo;
import com.neoris.cuentamovimiento.vos.AccountResponseVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<Flux<AccountResponseVo>> get(){
        return ResponseEntity.ok(accountService.findAll());
    }

    @PostMapping
    public ResponseEntity<Mono<AccountResponseVo>> post(
            @RequestBody Mono<AccountRequestVo> accountRequestDtoMono
    ){
        return ResponseEntity.ok(accountService.save(accountRequestDtoMono));
    }

    @PutMapping("{id}")
    public ResponseEntity<Mono<AccountResponseVo>> put(
            @PathVariable("id") Long id, @RequestBody Mono<AccountRequestVo> accountRequestDtoMono
    ){
        return ResponseEntity.ok(accountService.update(id, accountRequestDtoMono));
    }

    @PatchMapping("{id}")
    public ResponseEntity<Mono<AccountResponseVo>> patch(
            @PathVariable("id") Long id, @RequestBody Mono<AccountRequestVo> accountRequestDtoMono
    ){
        return ResponseEntity.ok(accountService.patch(id, accountRequestDtoMono));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Mono<Void>> delete(@PathVariable("id") Long id){
        return ResponseEntity.ok(accountService.delete(id));
    }
}
