package com.learning.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.entity.Authority;


@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String>{
}
