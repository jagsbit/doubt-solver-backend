package com.project.doubt_solver.service.impl;

import com.project.doubt_solver.exceptions.ResourceNotFoundException;
import com.project.doubt_solver.model.Users;
import com.project.doubt_solver.payloads.PaymentRequestDto;
import com.project.doubt_solver.repository.UserRepository;
import com.project.doubt_solver.service.PaymentService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {


    @Value("${razorpay.key}")
    private String razorpayKey;
    @Value("${razorpay.secret}")
    private String razorpaySecret;

    @Autowired
    private UserRepository userRepository;
    @Override
    public String createOrder(PaymentRequestDto requestDto) throws RazorpayException {
        var client=new RazorpayClient(razorpayKey, razorpaySecret);
        int amount=requestDto.getAmount();
        JSONObject ob=new JSONObject();
        ob.put("amount",amount*100);
        ob.put("currency","INR");
        ob.put("receipt","txn_235425");

        // creating new order

        Order order =client.orders.create(ob);

        //System.out.println(order);

        //return "Payment Started"+requestDto.getAmount()+" "+requestDto.getUsername();
        return order.toString();
    }

    @Override
    public String makeUserPro(int userId) {
        Users user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","userId",userId));
        user.setRole("pro");
        userRepository.save(user);
        return "user updated to pro";
    }
}
