package com.td1Rest.demo.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.td1Rest.demo.model.ServerModel;

@Repository
public interface ServerRepository extends JpaRepository<ServerModel, Long> {
  
}
