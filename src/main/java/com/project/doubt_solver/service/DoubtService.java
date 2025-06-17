package com.project.doubt_solver.service;

import com.project.doubt_solver.payloads.DoubtDto;
import com.project.doubt_solver.payloads.DoubtResponse;

import java.util.List;

public interface DoubtService {

    DoubtDto saveDoubt(DoubtDto doubtDto,int userId,int catId);
    DoubtResponse getAllDoubts(int pageNumber, int pageSize, String sortBy, String sortDir);
    String deleteDoubt(int doubtId);
    DoubtDto getDoubt(int doubtId);
    DoubtResponse getDoubtsByUser(int userId,int pageNumber, int pageSize, String sortBy,String sortDir);
    DoubtResponse getDoubtByCategory(int catId,int pageNumber, int pageSize, String sortBy,String sortDir);
    List<DoubtDto> searchDoubt(String keyword);

}
