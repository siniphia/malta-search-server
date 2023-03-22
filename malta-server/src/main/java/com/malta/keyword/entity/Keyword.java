package com.malta.keyword.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Keyword {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "keyword_seq")
    private Long id;

    @Column(unique = true, updatable = false)
    private String keyword;

    private Integer counter;

    @CreatedDate
    private LocalDateTime createdTime;

    @LastModifiedDate
    private LocalDateTime modifiedTime;

    public static Keyword of(String keyword) {
        return new Keyword(null, keyword, 1, null, null);
    }

    public Keyword increaseCounter() {
        this.counter += 1;
        return this;
    }
}
