package com.sboard.service;

import com.sboard.dto.TermsDTO;
import com.sboard.entity.Terms;
import com.sboard.repository.TermsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TermsService {
    private final TermsRepository termsRepository;

    public List<TermsDTO> selectTerms(){
        List<Terms> termsall = termsRepository.findAll();
        List<TermsDTO> termsDTO = termsall
                .stream()
                .map(entity -> entity.toDTO())
                .collect(Collectors.toList());
        return termsDTO;
    }
}
