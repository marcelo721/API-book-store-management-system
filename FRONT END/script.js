// URL da API (ajuste conforme necessário)
const API_URL = "http://localhost:8080/api/v1"; // Substitua com o endpoint correto da sua API

// Selecionando elementos do DOM
const loginForm = document.getElementById("loginForm");
const registerForm = document.getElementById("registerForm");

// Função para fazer login
async function login(email, password) {
    try {
        const response = await fetch(`${API_URL}/login`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ email, password }),
        });

        if (!response.ok) {
            const error = await response.json();
            throw new Error(error.message || "Erro ao fazer login");
        }

        alert("Login realizado com sucesso!");
        window.location.href = "welcome.html"; // Redireciona para a tela de boas-vindas

    } catch (error) {
        alert(`Erro: ${error.message}`);
    }
}

// Função para registrar um novo usuário
async function register(name, email, password) {
    try {
        const response = await fetch(`${API_URL}/users`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ name, email, password }),
        });

        if (!response.ok) {
            const error = await response.json();
            throw new Error(error.message || "Erro ao cadastrar usuário");
        }

        alert("Usuário cadastrado com sucesso!");
        window.location.href = "index.html"; // Redireciona para a tela de login
    } catch (error) {
        alert(`Erro: ${error.message}`);
    }
}

// Evento de submissão do formulário (login)
if (loginForm) {
    loginForm.addEventListener("submit", (e) => {
        e.preventDefault();
        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;
        login(email, password);
    });
}

// Evento de submissão do formulário (login)
if (loginForm) {
    loginForm.addEventListener("submit", (e) => {
        e.preventDefault();
        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;
        login(email, password);
    });
}

// Evento de submissão do formulário de cadastro
if (registerForm) {
    registerForm.addEventListener("submit", (e) => {
        e.preventDefault();
        const name = document.getElementById("name").value;
        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;
        register(name, email, password);
    });
}
