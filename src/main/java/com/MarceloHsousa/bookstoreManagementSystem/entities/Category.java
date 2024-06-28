package com.MarceloHsousa.bookstoreManagementSystem.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.EnableMBeanExport;

import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_category", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Builder.Default
    @ManyToMany(mappedBy = "categories")
    private Set<Book> books = new HashSet<>();

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
