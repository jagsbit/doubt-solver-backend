package com.project.doubt_solver.controller;

import com.project.doubt_solver.model.Category;
import com.project.doubt_solver.payloads.CatDto;
import com.project.doubt_solver.service.impl.CatServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cat")
public class CatController {

    @Autowired
    private CatServiceImpl catService;

    @PostMapping
    public ResponseEntity<CatDto> createCategory(@Valid  @RequestBody CatDto catDto){
        CatDto savedCat=catService.createCategory(catDto);
        return new ResponseEntity<>(savedCat, HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<List<CatDto>> getAllCategory(){
        List<CatDto> allCats=catService.getAllCategory();
        return new ResponseEntity<>(allCats,HttpStatus.OK);
    }
    @GetMapping("/{catId}")
    public ResponseEntity<CatDto> getCategoryById(@PathVariable int catId){
        CatDto catDto=catService.getCategory(catId);
        return new ResponseEntity<>(catDto,HttpStatus.OK);
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<String> deleteCategory(@PathVariable int catId){
        String message=catService.deleteCategory(catId);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }
}
