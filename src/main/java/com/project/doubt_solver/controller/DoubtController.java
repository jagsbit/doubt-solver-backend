package com.project.doubt_solver.controller;

import com.project.doubt_solver.config.AppConstants;
import com.project.doubt_solver.payloads.DoubtDto;
import com.project.doubt_solver.payloads.DoubtResponse;
import com.project.doubt_solver.service.impl.DoubtServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doubts")
public class DoubtController {

    @Autowired
    private DoubtServiceImpl doubtService;
    // save doubt
    @PostMapping("/user/{userId}/category/{catId}")
    public ResponseEntity<DoubtDto> saveDoubt(@RequestBody DoubtDto doubtDto,@PathVariable int userId,
                                              @PathVariable int catId){

        DoubtDto savedDoubt=doubtService.saveDoubt(doubtDto,userId,catId);
        return new ResponseEntity<>(savedDoubt, HttpStatus.CREATED);
    }

    // get All doubts
    @GetMapping
    public ResponseEntity<DoubtResponse> getAllDoubt(@RequestParam(value = "pageNumber", defaultValue =AppConstants.PAGE_NUMBER , required = false) int pageNumber,
                                                     @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
                                                     @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                                     @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir){
        DoubtResponse allDoubts=doubtService.getAllDoubts(pageNumber, pageSize,sortBy,sortDir);
        return new ResponseEntity<>(allDoubts,HttpStatus.OK);
    }

    // get single doubt
    @GetMapping("/{doubtId}")
    public ResponseEntity<DoubtDto> getDoubt(@PathVariable int doubtId){
        DoubtDto doubtDto= doubtService.getDoubt(doubtId);
        return new ResponseEntity<>(doubtDto,HttpStatus.OK);
    }

    // get doubt by user

    @GetMapping("user/{userId}")
    public ResponseEntity<DoubtResponse> getDoubtsByUser(@PathVariable int userId,@RequestParam(value = "pageNumber", defaultValue =AppConstants.PAGE_NUMBER , required = false) int pageNumber,
                                                          @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
                                                          @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                                          @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir){
        DoubtResponse doubtDtoList=doubtService.getDoubtsByUser(userId,pageNumber, pageSize,sortBy,sortDir);
        return new ResponseEntity<>(doubtDtoList,HttpStatus.OK);
    }

    // get doubts by category

    @GetMapping("category/{catId}")
    public ResponseEntity<DoubtResponse> getDoubtByCategory(@PathVariable int catId,@RequestParam(value = "pageNumber", defaultValue =AppConstants.PAGE_NUMBER , required = false) int pageNumber,
                                                             @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) int pageSize,
                                                             @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                                             @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir){
        DoubtResponse doubtDtoList=doubtService.getDoubtByCategory(catId,pageNumber, pageSize,sortBy,sortDir);
        return new ResponseEntity<>(doubtDtoList,HttpStatus.OK);
    }

    @DeleteMapping("/{doubtId}")
    public ResponseEntity<String> deleteDoubt(@PathVariable int doubtId){
        String response=doubtService.deleteDoubt(doubtId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
