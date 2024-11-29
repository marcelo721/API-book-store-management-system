// URL da API (ajuste conforme necessário)
const API_URL = "http://localhost:8080/api/v1"; // Substitua com sua URL base da API
const TOKEN = localStorage.getItem("authToken"); // Pegue o token armazenado

// Verifica se o usuário está autenticado
if (!TOKEN) {
    alert("Você precisa estar logado para acessar esta página!");
    window.location.href = "index.html"; // Redireciona para a tela de login
}

// Função para criar Categoria
document.getElementById("categoryForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const name = document.getElementById("categoryName").value;
    const description = document.getElementById("categoryDescription").value;

    try {
        const response = await fetch(`${API_URL}/categories`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${TOKEN}`,
            },
            body: JSON.stringify({ name, description }),
        });
        if (!response.ok) throw new Error("Erro ao criar categoria");
        alert("Categoria criada com sucesso!");
    } catch (error) {
        alert(error.message);
    }
});


// Função para criar Autor
document.getElementById("authorForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const name = document.getElementById("authorName").value;
    const nationality = document.getElementById("authorNationality").value;
    const birthDate = document.getElementById("authorBirthDate").value;

    try {
        const response = await fetch(`${API_URL}/authors`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${TOKEN}`,
            },
            body: JSON.stringify({ name, nationality, birthDate }),
        });
        if (!response.ok) throw new Error("Erro ao criar autor");
        alert("Autor criado com sucesso!");
    } catch (error) {
        alert(error.message);
    }
});



// Função para criar Livro
document.getElementById("bookForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const title = document.getElementById("bookTitle").value;
    const description = document.getElementById("bookDescription").value;
    const isbn = document.getElementById("bookIsbn").value;
    const authorId = document.getElementById("bookAuthorId").value;
    const categoryId = document.getElementById("bookCategoryId").value;

    // Criando os objetos conforme esperado pelo backend
    const author = { id: authorId };
    const categories = [{ id: categoryId }];

    try {
        const response = await fetch(`${API_URL}/books`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${TOKEN}`,
            },
            body: JSON.stringify({ title, description, isbn, author, categories }), // Enviando os objetos completos
        });

        if (!response.ok) throw new Error("Erro ao criar livro");
        alert("Livro criado com sucesso!");
    } catch (error) {
        alert(error.message);
    }
});


// Renderiza a lista com paginação e estrutura específica para cada tipo de objeto
function renderListWithPagination(data, type, fetchFunction) {
    let listElement = document.getElementById(`${type}List`);
    listElement.innerHTML = '';

    if (!data.content || data.content.length === 0) {
        listElement.innerHTML = `<li>Nenhum ${type} encontrado.</li>`;
        return;
    }

    // Renderizar os itens
    data.content.forEach(item => {
        const li = document.createElement('li');

        if (type === 'category') {
            // Categoria
            li.innerHTML = `
                <strong>ID:</strong> ${item.id} <br>
                <strong>Nome:</strong> ${item.name} <br>
                <strong>Descrição:</strong> ${item.description}
            `;
        } else if (type === 'author') {
            // Autor
            li.innerHTML = `
                <strong>ID:</strong> ${item.id} <br>
                <strong>Nome:</strong> ${item.name} <br>
                <strong>Nacionalidade:</strong> ${item.nationality} <br>
                <strong>Livros:</strong> 
                <ul>
                    ${item.books.map(book => `<li>${book.title}`).join('')}
                </ul>
            `;
        } else if (type === 'book') {
            // Livro
            li.innerHTML = `
                <strong>ID:</strong> ${item.id} <br>
                <strong>Título:</strong> ${item.title} <br>
                <strong>Descrição:</strong> ${item.description} <br>
                <strong>Autor:</strong> ${item.author.name} <br>
                <strong>Categorias:</strong> 
                <ul>
                    ${item.categories.map(category => `<li>${category.name}</li>`).join('')}
                </ul>
            `;
        }

        listElement.appendChild(li);
    });

    // Renderizar paginação
    const pagination = document.createElement('div');
    pagination.classList.add('pagination');

    if (!data.first) {
        const prevButton = document.createElement('button');
        prevButton.textContent = "Anterior";
        prevButton.onclick = () => fetchFunction(data.pageable.pageNumber - 1);
        pagination.appendChild(prevButton);
    }

    if (!data.last) {
        const nextButton = document.createElement('button');
        nextButton.textContent = "Próximo";
        nextButton.onclick = () => fetchFunction(data.pageable.pageNumber + 1);
        pagination.appendChild(nextButton);
    }

    listElement.appendChild(pagination);
}

// Função genérica para buscar páginas
async function fetchPage(endpoint, page = 0, type) {
    try {
        const response = await fetch(`${API_URL}/${endpoint}?page=${page}`, {
            headers: {
                "Authorization": `Bearer ${TOKEN}`,
            },
        });
        if (!response.ok) throw new Error(`Erro ao buscar ${type}`);
        const data = await response.json();
        renderListWithPagination(data, type, (newPage) => fetchPage(endpoint, newPage, type));
    } catch (error) {
        console.error(`Erro ao buscar ${type}:`, error);
    }
}

// Funções específicas para cada tipo de entidade
function fetchCategories(page = 0) {
    fetchPage('categories', page, 'category');
}

function fetchAuthors(page = 0) {
    fetchPage('authors', page, 'author');
}

function fetchBooks(page = 0) {
    fetchPage('books', page, 'book');
}

document.getElementById("loadUsersButton").addEventListener("click", async () => {
    try {
        // Fazendo a requisição GET para buscar todos os usuários
        const response = await fetch(`${API_URL}/users`, {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${TOKEN}`, // Usando o token para autenticação
            },
        });

        if (!response.ok) throw new Error("Erro ao buscar usuários");

        // Converte a resposta JSON em um objeto JavaScript
        const data = await response.json();

        // Processar a resposta para exibir os usuários
        displayUsers(data.content, data.page, data.totalPages);

    } catch (error) {
        alert(error.message);
    }
});

// Função para exibir os usuários na tela
function displayUsers(users) {
    const usersList = document.getElementById("usersList");
    usersList.innerHTML = ""; // Limpa a lista de usuários

    // Verifica se encontrou usuários
    if (users.length === 0) {
        usersList.innerHTML = "<p>Nenhum usuário encontrado.</p>";
        return;
    }

    // Cria uma lista de usuários
    users.forEach(user => {
        const userItem = document.createElement("li");
        
        // Exibe as informações do usuário
        userItem.innerHTML = `
            <strong>Nome:</strong> ${user.name} <br>
            <strong>E-mail:</strong> ${user.email} <br>
            <strong>Função:</strong> ${user.role} <br>
            <strong>Status da Conta:</strong> ${user.statusAccount} <br>
            <strong>ID:</strong> ${user.id} <br><br>
        `;
        
        usersList.appendChild(userItem);
    });
}

document.getElementById("loadUserByIdButton").addEventListener("click", async () => {
    // Obtém o ID do usuário digitado no campo de input
    const userId = document.getElementById("userId").value;

    // Verifica se o ID é válido
    if (!userId) {
        alert("Por favor, insira um ID de usuário válido.");
        return;
    }

    try {
        // Fazendo a requisição GET para buscar o usuário pelo ID
        const response = await fetch(`${API_URL}/users/${userId}`, {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${TOKEN}`, // Usando o token para autenticação
            },
        });

        // Verifica se a resposta foi bem-sucedida
        if (!response.ok) throw new Error("Erro ao buscar o usuário");

        // Converte a resposta JSON em um objeto JavaScript
        const user = await response.json();

        // Exibe as informações do usuário
        displayUserDetails(user);

    } catch (error) {
        alert(error.message);
    }
});

// Função para exibir os detalhes do usuário
function displayUserDetails(user) {
    const userDetails = document.getElementById("userDetails");
    userDetails.innerHTML = ""; // Limpa os detalhes anteriores

    // Exibe as informações do usuário
    userDetails.innerHTML = `
        <strong>Nome:</strong> ${user.name} <br>
        <strong>E-mail:</strong> ${user.email} <br>
        <strong>Função:</strong> ${user.role} <br>
        <strong>Status da Conta:</strong> ${user.statusAccount} <br>
        <strong>ID:</strong> ${user.id} <br><br>
    `;
}

// Eventos de clique
document.getElementById('fetchCategoriesByPage').addEventListener('click', () => fetchCategories());
document.getElementById('fetchAuthors').addEventListener('click', () => fetchAuthors());
document.getElementById('fetchBooks').addEventListener('click', () => fetchBooks());
document.getElementById('fetchUserById').addEventListener('click', fetchUserById);
