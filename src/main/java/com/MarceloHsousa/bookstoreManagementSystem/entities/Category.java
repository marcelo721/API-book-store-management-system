package com.MarceloHsousa.bookstoreManagementSystem.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.EnableMBeanExport;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_category", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "categories_books",
            joinColumns = @JoinColumn(name = "category_fk"),
            inverseJoinColumns = @JoinColumn(name = "book_fk")
    )
    private List<Book> books = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category category)) return false;
        return getId().equals(category.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
