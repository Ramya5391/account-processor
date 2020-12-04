package com.intuit.accountprocessor.api;

import com.intuit.accountprocessor.domain.Account;
import com.intuit.accountprocessor.dto.AccountRequest;
import com.intuit.accountprocessor.dto.Response;
import com.intuit.accountprocessor.service.AccountProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/account/")
public class AccountProcessorController {

    @Autowired
    AccountProcessorService accountProcessorService;


    @PostMapping("address/standadize" )
    public ResponseEntity<Response> processAccount(@Valid @RequestBody AccountRequest accountDTO)
    {
        Account account=  null;
        try{
            account=this.accountProcessorService.processAndSaveAccount(accountDTO);
        }catch(Exception e)
        {
            return ResponseEntity.badRequest()
                    .body(Response.builder().errorMessage(e.getMessage()).build());
        }
        if(account==null)
        {
            return ResponseEntity.badRequest()
                    .body(Response.builder().errorMessage("Invalid Address").build());
        }

        return ResponseEntity.ok(Response.builder().result(account).build());
    }

    @GetMapping(path="/{accountId}" )
    public ResponseEntity<Response> getAccount(@PathVariable(required = true) Long accountId)
    {
        Account account= accountProcessorService.getDetails(accountId);
        if(account==null)
        {
            return ResponseEntity.badRequest()
                    .body(Response.builder().errorMessage("Account not found").build());
        }

        return ResponseEntity.ok(Response.builder().result(account).build());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
