# Spring Boot Product API

Esta é uma API RESTful simples para gerenciar produtos, construída usando Spring Boot. Ela permite operações CRUD (Create, Read, Update e Delete) para produtos, como criar novos produtos, visualizar todos os produtos, obter detalhes de um produto específico, atualizar e deletar produtos.

## Estrutura do Projeto

### Pacote: `com.thalesmonteiro.springboot.controller`

O pacote `controller` contém o `ProductController`, que gerencia as requisições HTTP para os endpoints da API relacionados aos produtos.

### Endpoints

1. **POST /products** - Salva um novo produto no banco de dados.
2. **GET /products** - Recupera uma lista de todos os produtos.
3. **GET /products/{id}** - Recupera os detalhes de um único produto, baseado no seu ID.
4. **PUT /products/{id}** - Atualiza os dados de um produto existente, identificado pelo ID.
5. **DELETE /products/{id}** - Deleta um produto existente pelo ID.

## Exemplo de Uso

### 1. Salvar um Produto (POST /products)

Cria um novo produto com os dados fornecidos.

**Exemplo de requisição:**
```json
{
  "name": "Produto A",
  "price": 10.99,
}
