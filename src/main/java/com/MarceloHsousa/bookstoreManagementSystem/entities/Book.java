package com.MarceloHsousa.bookstoreManagementSystem.entities;

import com.MarceloHsousa.bookstoreManagementSystem.entities.enums.Role;
import com.MarceloHsousa.bookstoreManagementSystem.entities.enums.StatusBook;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "books")
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "title", unique = false, nullable = false, length = 200)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "isbn" , nullable = false)
    private String isbn;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private StatusBook statusBook = StatusBook.INDISPONIVEL;

    @JoinColumn(name = "author_id", nullable = false)
    @ManyToOne
    private Author author;

    @ManyToMany(fetch = FetchType.EAGER)
    @Builder.Default
    @JsonIgnore
    private List<Category> categories = new ArrayList<>();

    @LastModifiedDate
    @Column(name = "modification_date")
    private LocalDateTime modificationDate;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @LastModifiedBy
    @Column(name = "modified_by")
    private String modifiedBy;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return getId().equals(book.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
