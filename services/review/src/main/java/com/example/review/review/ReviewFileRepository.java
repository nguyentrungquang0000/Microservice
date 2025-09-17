package com.example.review.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewFileRepository extends JpaRepository<ReviewFile, String> {
}
