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

        const data = await response.json();
        const token = data.token; // Recebe o token do backend

        if (!token) {
            throw new Error("Token não recebido!");
        }

        // Armazena o token no localStorage
        localStorage.setItem("authToken", token);

        window.location.href = "admin.html"; // Redireciona para a página de boas-vindas
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

        alert("Usuário cadastrado com sucesso!, pra continuar verifique seu email e ative sua conta!");
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
