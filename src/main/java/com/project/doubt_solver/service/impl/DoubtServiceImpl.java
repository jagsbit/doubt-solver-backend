package com.project.doubt_solver.service.impl;

import com.project.doubt_solver.exceptions.ResourceNotFoundException;
import com.project.doubt_solver.model.Category;
import com.project.doubt_solver.model.Doubt;
import com.project.doubt_solver.model.Users;
import com.project.doubt_solver.payloads.DoubtDto;
import com.project.doubt_solver.payloads.DoubtResponse;
import com.project.doubt_solver.repository.CatRepository;
import com.project.doubt_solver.repository.DoubtRepository;
import com.project.doubt_solver.repository.UserRepository;
import com.project.doubt_solver.service.DoubtService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.ReadOnlyFileSystemException;
import java.util.List;

@Service
public class DoubtServiceImpl implements DoubtService {

    @Autowired
    private DoubtRepository doubtRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CatRepository catRepo;

    @Override
    @Transactional
    public DoubtDto saveDoubt(DoubtDto doubtDto,int userId,int catId) {
        Doubt doubt = modelMapper.map(doubtDto, Doubt.class);


        // Fetch user from DB
        Users user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User","userId",userId));
        doubt.setUser(user);

        // Fetch category from DB
        Category category = catRepo.findById(catId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","catId",catId));
        doubt.setCategory(category);

        Doubt savedDoubt = doubtRepo.save(doubt);
        return modelMapper.map(savedDoubt, DoubtDto.class);
    }


    @Override
    public DoubtResponse getAllDoubts(int pageNumber, int pageSize, String sortBy,String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Doubt> doubtList=doubtRepo.findAll(pageable);
        List<DoubtDto> doubtDtoList=doubtList.stream()
                .map((doubt -> modelMapper.map(doubt, DoubtDto.class)))
                .toList();
        DoubtResponse resp=new DoubtResponse();
        resp.setContent(doubtDtoList);
        resp.setPageNumber(doubtList.getNumber());
        resp.setPageSize(doubtList.getSize());
        resp.setTotalElements(doubtList.getTotalElements());
        resp.setTotalPages(doubtList.getTotalPages());
        resp.setLastPage(doubtList.isLast());

        return resp;
    }

    @Override
    public String deleteDoubt(int doubtId) {
        Doubt doubt=doubtRepo.findById(doubtId)
                .orElseThrow(()->new ResourceNotFoundException("Doubt","doubtId",doubtId));
        doubtRepo.delete(doubt);
        return "Doubt deleted successfully";
    }

    @Override
    public DoubtDto getDoubt(int doubtId) {
        Doubt doubt=doubtRepo.findById(doubtId)
                .orElseThrow(()->new ResourceNotFoundException("Doubt","doubtId",doubtId));

        return modelMapper.map(doubt,DoubtDto.class);
    }

    @Override
    public DoubtResponse getDoubtsByUser(int userId,int pageNumber, int pageSize, String sortBy,String sortDir) {
        Users user=userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","userId",userId));
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Doubt>  doubtList=doubtRepo.findByUser(user,pageable);
        List<DoubtDto> doubtDtoList=doubtList.stream()
                .map((doubt -> modelMapper.map(doubt, DoubtDto.class)))
                .toList();
        DoubtResponse resp=new DoubtResponse();
        resp.setContent(doubtDtoList);
        resp.setPageNumber(doubtList.getNumber());
        resp.setPageSize(doubtList.getSize());
        resp.setTotalElements(doubtList.getTotalElements());
        resp.setTotalPages(doubtList.getTotalPages());
        resp.setLastPage(doubtList.isLast());

        return resp;
    }

    @Override
    public DoubtResponse getDoubtByCategory(int catId,int pageNumber, int pageSize, String sortBy,String sortDir) {
        Category category=catRepo.findById(catId)
                .orElseThrow(()->new ResourceNotFoundException("Category","catId",catId));
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Doubt> doubtList=doubtRepo.findByCategory(category,pageable);
        List<DoubtDto> doubtDtoList=doubtList.stream()
                .map((doubt -> modelMapper.map(doubt, DoubtDto.class)))
                .toList();
        DoubtResponse resp=new DoubtResponse();
        resp.setContent(doubtDtoList);
        resp.setPageNumber(doubtList.getNumber());
        resp.setPageSize(doubtList.getSize());
        resp.setTotalElements(doubtList.getTotalElements());
        resp.setTotalPages(doubtList.getTotalPages());
        resp.setLastPage(doubtList.isLast());

        return resp;
    }

    @Override
    public List<DoubtDto> searchDoubt(String keyword) {

        return List.of();
    }
}
