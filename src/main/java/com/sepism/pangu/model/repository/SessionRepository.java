package com.sepism.pangu.model.repository;

import com.sepism.pangu.model.authentication.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface SessionRepository extends JpaRepository<Session, Long> {
}
