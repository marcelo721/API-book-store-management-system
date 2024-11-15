-- Inserir autores
INSERT INTO authors (name, birth_date, nationality, creation_date, modification_date)
VALUES
    ('George', '1977-12-12', 'alemão', NOW(), NOW()),
    ('lucas deniro', '1977-12-12', 'ingles', NOW(), NOW());

-- Inserir usuários
INSERT INTO users (name, email, password, role, creation_date, modification_date)
VALUES
    ('marcelo henrique de sousa', 'marceloHenrique@gmail.com', '12345678', 'CLIENT', NOW(), NOW()),
    ('admin test', 'admin@gmail.com', '12345678', 'ADMIN', NOW(), NOW());

-- Inserir categorias
INSERT INTO categories (name_category, description, creation_date, modification_date)
VALUES
    ('Adventure', 'books of adventure', NOW(), NOW()),
    ('Terror', 'books of terror', NOW(), NOW()),
    ('Ação', 'books of action', NOW(), NOW());

-- Inserir livros
INSERT INTO books (title, description, isbn, status, author_id, creation_date, modification_date)
VALUES
    ('i am the legend', 'livro de vampiros', '123123213', 'AVAILABLE',
     (SELECT id FROM authors WHERE name = 'George'), NOW(), NOW());

-- Relacionar livro com categorias
INSERT INTO categories_books (book_id, category_id)
VALUES
    ((SELECT id FROM books WHERE title = 'i am the legend'),
     (SELECT id FROM categories WHERE name_category = 'Terror'));

-- Inserir empréstimos
-- Criação de tabela loan (se não existir)
CREATE TABLE loans (
                       id BIGINT NOT NULL AUTO_INCREMENT,
                       user_id BIGINT NOT NULL,
                       book_id BIGINT NOT NULL,
                       return_due_date DATE NOT NULL,
                       creation_date DATETIME(6),
                       modification_date DATETIME(6),
                       PRIMARY KEY (id),
                       FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                       FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- Inserir empréstimo
INSERT INTO loans (user_id, book_id, return_due_date, creation_date, modification_date)
VALUES
    ((SELECT id FROM users WHERE email = 'marceloHenrique@gmail.com'),
     (SELECT id FROM books WHERE title = 'i am the legend'),
     '2025-12-12', NOW(), NOW());
