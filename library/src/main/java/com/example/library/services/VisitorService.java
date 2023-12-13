package com.example.library.services;

import com.example.library.models.Visitor;
import com.example.library.repositories.VisitorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitorService {
    private final VisitorRepository visitorRepository;

    public List<Visitor> listVisitors(String phoneNumber){
        if (phoneNumber != null) return visitorRepository.findByPhoneNumber(phoneNumber);
        return visitorRepository.findAll();
    }

    public void saveVisitor(Visitor visitor) {
        visitorRepository.save(visitor);
    }

    public void deleteVisitor(Long id){
        if (getVisitorByID(id) != null) {
            visitorRepository.deleteById(id);
        }
    }

    public Visitor getVisitorByID(Long id){
        return visitorRepository.findById(id).orElse(null);
    }
}
