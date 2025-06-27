package com.project.doubt_solver.controller;

import com.project.doubt_solver.payloads.PaymentRequestDto;
import com.project.doubt_solver.service.impl.PaymentServiceImpl;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/api/payment")
public class PaymentController {


    @Autowired
    private PaymentServiceImpl paymentService;
    @PostMapping("/create-order")
    public ResponseEntity<String> createOrder(@RequestBody PaymentRequestDto requestDto) throws RazorpayException {
        String response=paymentService.createOrder(requestDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/make-user-pro/{userId}")
    public ResponseEntity<String> makeUserPro(@PathVariable int userId){

        String response=paymentService.makeUserPro(userId);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

}
