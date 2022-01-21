package com.irlix.task.service.impl;

import com.irlix.task.entity.TransactionEntity;
import com.irlix.task.repository.TransactionRepository;
import com.irlix.task.service.TransactionService;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public TransactionEntity save(TransactionEntity entity) {
        return transactionRepository.save(entity);
    }

    @Override
    public TransactionEntity findById(Long id) throws NotFoundException {
        return transactionRepository.findById(id).orElseThrow(()-> new NotFoundException(
                String.format("No such transaction with id: %d", id)));
    }

    @Override
    public List<TransactionEntity> getAll() {
        return transactionRepository.findAll();
    }
}
