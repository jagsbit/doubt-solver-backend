package com.project.doubt_solver.payloads;

import com.project.doubt_solver.model.Users;

public class Pair {
   public Users user;
   public int count;
   public Pair(Users user,int count){
       this.user=user;
       this.count=count;
   }

}
