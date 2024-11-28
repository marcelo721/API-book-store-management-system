CREATE TABLE authors (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(40) NOT NULL,
    birth_date DATE NOT NULL,
    nationality VARCHAR(255) NOT NULL,
    creation_date DATETIME(6),
    modification_date DATETIME(6),
    created_by VARCHAR(255),
    modified_by VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE books (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,
    description VARCHAR(255) NOT NULL,
    isbn VARCHAR(255) NOT NULL,
    status ENUM('AVAILABLE', 'UNAVAILABLE') NOT NULL,
    creation_date DATETIME(6),
    modification_date DATETIME(6),
    created_by VARCHAR(255),
    modified_by VARCHAR(255),
    author_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (author_id) REFERENCES authors(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE categories (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name_category VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    creation_date DATETIME(6),
    modification_date DATETIME(6),
    created_by VARCHAR(255),
    modified_by VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE categories_books (
    book_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (book_id, category_id),
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(8) NOT NULL,
    role ENUM('ADMIN', 'CLIENT') NOT NULL,
    creation_date DATETIME(6),
    modification_date DATETIME(6),
    created_by VARCHAR(255),
    modified_by VARCHAR(255),
    PRIMARY KEY (id),
    UNIQUE (email)
) ENGINE=InnoDB;

ALTER TABLE books
    ADD CONSTRAINT FK_books_authors
    FOREIGN KEY (author_id) REFERENCES authors(id);

ALTER TABLE categories_books
    ADD CONSTRAINT FK_categories_books_books
    FOREIGN KEY (book_id) REFERENCES books(id);

ALTER TABLE categories_books
    ADD CONSTRAINT FK_categories_books_categories
    FOREIGN KEY (category_id) REFERENCES categories(id);
