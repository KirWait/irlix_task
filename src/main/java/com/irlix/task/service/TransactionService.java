package com.irlix.task.service;

import com.irlix.task.entity.TransactionEntity;
import javassist.NotFoundException;

import java.util.List;

public interface TransactionService {
    TransactionEntity save(TransactionEntity entity);
    TransactionEntity findById(Long id) throws NotFoundException;
    List<TransactionEntity> getAll();
}
