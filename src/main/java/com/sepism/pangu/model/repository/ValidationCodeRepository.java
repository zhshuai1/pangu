package com.sepism.pangu.model.repository;

import com.sepism.pangu.model.authentication.ValidationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public interface ValidationCodeRepository extends JpaRepository<ValidationCode, String> {
}
