const API_URL = "http://localhost:8080/api/produtos";

// Listar produtos
async function listarProdutos() {
    const response = await fetch(API_URL);
    const produtos = await response.json();

    const tabela = document.getElementById("produtoTabela");
    tabela.innerHTML = "";

    produtos.forEach(produto => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${produto.id}</td>
            <td>${produto.nome}</td>
            <td>R$ ${produto.preco.toFixed(2)}</td>
            <td class="actions">
                <button class="edit" onclick="editarProduto(${produto.id}, '${produto.nome}', ${produto.preco})">Editar</button>
                <button class="delete" onclick="deletarProduto(${produto.id})">Excluir</button>
            </td>
        `;
        tabela.appendChild(row);
    });
}

// Adicionar ou atualizar produto
async function salvarProduto(event) {
    event.preventDefault();

    const id = document.getElementById("produtoId").value;
    const nome = document.getElementById("nome").value;
    const preco = document.getElementById("preco").value;

    const produto = { nome, preco: parseFloat(preco) };

    if (id) {
        // Atualizar produto
        await fetch(`${API_URL}/${id}`, {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(produto)
        });
    } else {
        // Criar novo produto
        await fetch(API_URL, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(produto)
        });
    }

    document.getElementById("produtoForm").reset();
    listarProdutos();
}

// Editar produto
function editarProduto(id, nome, preco) {
    document.getElementById("produtoId").value = id;
    document.getElementById("nome").value = nome;
    document.getElementById("preco").value = preco;
}

// Deletar produto
async function deletarProduto(id) {
    if (confirm("Tem certeza que deseja excluir este produto?")) {
        await fetch(`${API_URL}/${id}`, { method: "DELETE" });
        listarProdutos();
    }
}

// Evento para o formulário
document.getElementById("produtoForm").addEventListener("submit", salvarProduto);

// Carregar a lista ao iniciar
listarProdutos();
