package com.project.doubt_solver.service;

import com.project.doubt_solver.payloads.PaymentRequestDto;
import com.razorpay.RazorpayException;

public interface PaymentService {

    String createOrder(PaymentRequestDto requestDto) throws RazorpayException;
    String makeUserPro(int userId);
}
