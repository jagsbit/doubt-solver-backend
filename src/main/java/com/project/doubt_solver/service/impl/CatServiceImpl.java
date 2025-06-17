package com.project.doubt_solver.service.impl;

import com.project.doubt_solver.exceptions.ResourceNotFoundException;
import com.project.doubt_solver.model.Category;
import com.project.doubt_solver.payloads.CatDto;
import com.project.doubt_solver.repository.CatRepository;
import com.project.doubt_solver.service.CatService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatServiceImpl implements CatService {

    @Autowired
    private CatRepository catRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CatDto createCategory(CatDto catDto) {
        Category category=modelMapper.map(catDto,Category.class);
        Category savedCategory=catRepo.save(category);
        return modelMapper.map(savedCategory,CatDto.class);
    }

    @Override
    public List<CatDto> getAllCategory() {
        List<Category> categoryList=catRepo.findAll();
        List<CatDto> catDtoList=categoryList.stream()
                .map((category -> modelMapper.map(category,CatDto.class)))
                .toList();

        return catDtoList;
    }

    @Override
    public String deleteCategory(int catId) {
        Category category=catRepo.findById(catId)
                .orElseThrow(()->new ResourceNotFoundException("Category","catId",catId));
        catRepo.delete(category);
        return "Category deleted SuccessFully";
    }

    @Override
    public CatDto getCategory(int catId) {
        Category category=catRepo.findById(catId)
                .orElseThrow(()->new ResourceNotFoundException("Category","catId",catId));
        return modelMapper.map(category,CatDto.class);
    }
}
