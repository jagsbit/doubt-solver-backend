package com.project.doubt_solver.service;

import com.project.doubt_solver.payloads.CatDto;

import java.util.List;

public interface CatService {
    CatDto createCategory(CatDto catDto);
    List<CatDto> getAllCategory();
    String deleteCategory(int catId);
    CatDto getCategory(int catId);
}
