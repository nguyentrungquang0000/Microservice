package com.example.review.review;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {

    @Query("""
    SELECT r
    FROM Review r
    WHERE r.productId = :productId
      AND r.createdAt <= COALESCE(:dateLast, r.createdAt)
    """)
    Page<Review> getReviewsPage(@Param("productId") String productId,
                                @Param("dateLast") LocalDateTime dateLast,
                                Pageable pageable);

    Optional<Review> findByProductId(String productId);

    @Query("SELECT r FROM Review r WHERE r.productId = :productId AND (r.reply IS NULL OR r.reply = '')")
    List<Review> findByProductIdAndReplyIsNullOrEmpty(@Param("productId") String productId);

}
