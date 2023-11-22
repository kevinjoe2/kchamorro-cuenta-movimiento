package com.kchamorro.cuentamovimiento.controllers;

import com.kchamorro.cuentamovimiento.vos.TransactionRequestVo;
import com.kchamorro.cuentamovimiento.vos.TransactionResponseVo;
import com.kchamorro.cuentamovimiento.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<Flux<TransactionResponseVo>> getAll(){
        return ResponseEntity.ok(transactionService.findAll());
    }

    @PostMapping
    public ResponseEntity<Mono<TransactionResponseVo>> post(
            @RequestBody TransactionRequestVo transactionRequestDto
    ){
        return ResponseEntity.ok(transactionService.save(transactionRequestDto));
    }

    @PutMapping("{id}")
    public ResponseEntity<Mono<TransactionResponseVo>> put(
            @PathVariable("id") Long id,
            @RequestBody TransactionRequestVo transactionRequestDto
    ){
        return ResponseEntity.ok(transactionService.update(id, transactionRequestDto));
    }

    @PatchMapping("{id}")
    public ResponseEntity<Mono<TransactionResponseVo>> patch(
            @PathVariable("id") Long id,
            @RequestBody TransactionRequestVo transactionRequestDto
    ){
        return ResponseEntity.ok(transactionService.patch(id, transactionRequestDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Mono<Void>> delete(
            @PathVariable("id") Long id
    ){
        return ResponseEntity.ok(transactionService.delete(id));
    }
}
