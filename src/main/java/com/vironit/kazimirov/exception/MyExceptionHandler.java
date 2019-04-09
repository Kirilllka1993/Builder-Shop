package com.vironit.kazimirov.exception;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class MyExceptionHandler {
    private static final Logger LOGGER = Logger.getLogger(MyExceptionHandler.class.getName());

    @ExceptionHandler({ClientNotFoundException.class})
    public ResponseEntity<CustomErrorResponce> absentOfClient() {
        LOGGER.error("User Not found");
        CustomErrorResponce errors = new CustomErrorResponce(LocalDateTime.now(), 500, "User not found");
        return new ResponseEntity<>(errors, new HttpHeaders(), HttpStatus.valueOf(errors.getStatus()));
    }

    @ExceptionHandler({RepeatitionException.class})
    public ResponseEntity<CustomErrorResponce> presentElement() {
        LOGGER.error("Repeat entities ");
        CustomErrorResponce errors = new CustomErrorResponce(LocalDateTime.now(), 400, "such element is present");
        return new ResponseEntity<>(errors, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({GoodException.class})
    public ResponseEntity<CustomErrorResponce> goodHandle() {
        LOGGER.error("Good Exception ");
        CustomErrorResponce errors = new CustomErrorResponce(LocalDateTime.now(), 400, "the discount can't be more then price");
        return new ResponseEntity<>(errors, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({GoodNotFoundException.class})
    public ResponseEntity<CustomErrorResponce> goodNotFoundHandle() {
        LOGGER.error("Such goodId is absent ");
        CustomErrorResponce errors = new CustomErrorResponce(LocalDateTime.now(), 500, "such goodId is absent");
        return new ResponseEntity<>(errors, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({SubsectionNotFoundException.class})
    public ResponseEntity<CustomErrorResponce> subsectionNotFoundHandle() {
        LOGGER.error("Such subsection is absent ");
        CustomErrorResponce errors = new CustomErrorResponce(LocalDateTime.now(), 500, "such subsection is absent");
        return new ResponseEntity<>(errors, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({PurposeNotFoundException.class})
    public ResponseEntity<CustomErrorResponce> purposeNotFoundHandle() {
        LOGGER.error("Such purpose is absent ");
        CustomErrorResponce errors = new CustomErrorResponce(LocalDateTime.now(), 500, "such purchase is absent");
        return new ResponseEntity<>(errors, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({PurchaseException.class})
    public ResponseEntity<CustomErrorResponce> purchaseHandle() {
        LOGGER.error("Purchase Exception ");
        CustomErrorResponce errors = new CustomErrorResponce(LocalDateTime.now(), 400, "purchaseException");
        return new ResponseEntity<>(errors, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Throwable.class})
    public ResponseEntity<CustomErrorResponce> allHandle(Throwable t) {
        LOGGER.error("Mistake");
        CustomErrorResponce errors = new CustomErrorResponce(LocalDateTime.now(), 400, t.toString());
        return new ResponseEntity<>(errors, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({CartItemNotFoundException.class})
    public ResponseEntity<CustomErrorResponce> cartemNotFoundHandle() {
        LOGGER.error("this cartItem is absent");
        CustomErrorResponce errors = new CustomErrorResponce(LocalDateTime.now(), 500,"such cartItem is absent");
        return new ResponseEntity<>(errors, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}
