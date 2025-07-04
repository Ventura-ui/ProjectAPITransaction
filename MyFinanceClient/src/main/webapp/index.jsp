<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MyFinance Client</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body>
    <div class="container mt-4">
        <h1 id="titulo" class="mb-4 text-center text-primary">MyFinance Client</h1>
        
        <div class="card p-3 mb-4 shadow-sm">
            <h4 class="card-title">Filtros: </h4>
            <div class="row g-3 align-items-end">
                <div class="col-md-auto">
                    <label for="filterCategory" class="form-label">Categoria: </label>
                    <select id="filterCategory" class="form-select">
                        <option value="Todas">Todas</option>
                        <option value="Alimentação">Alimentação</option>
                        <option value="Moradia">Moradia</option>
                        <option value="Lazer">Lazer</option>
                        <option value="Contas">Contas</option>
                        <option value="Saúde">Saúde</option>
                        <option value="Serviços">Serviços</option>
                        <option value="Transferências">Transferências</option>
                        <option value="Transporte">Transporte</option>
                        <option value="Investimentos">Investimentos</option>
                        <option value="Salário">Salário</option>
                        <option value="Não essenciais">Não essenciais</option>
                    </select>
                </div>
                <div class="col-md-auto">
                    <label for="filterType" class="form-label">Tipo: </label>
                    <select id="filterType" class="form-select">
                        <option value="Todas">Todas</option>
                        <option value="despesa">despesa</option>
                        <option value="receita">receita</option>
                    </select>
                </div>
                <div class="col-md-auto">
                    <button onclick="getFilterTransactions()" class="btn btn-primary me-2">Filtrar</button>
                    <button onclick="limparFiltragem()" class="btn btn-outline-secondary">Limpar filtros</button>
                </div>
            </div>
        </div>
        
        <h2 class="mt-5 mb-3 text-primary">Todas as Transações</h2>
        <div id="transactionsOutput" class="table-responsive">
            <table id="transactionsTable" class="table table-striped table-hover table-bordered">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Descrição</th>
                        <th>Valor</th>
                        <th>Tipo</th>
                        <th>Categoria</th>
                        <th>Data Criação</th>
                        <th>Ação 1</th>
                        <th>Ação 2</th>
                    </tr>
                </thead>
                <tbody id="transactionsTableBody">
                </tbody>
            </table>
            <pre id="transactionsJson" style="display:none;"></pre>
        </div>
        <div class="d-flex justify-content-between mt-3">
            <button id="prevPageBtn" onclick="goToPrevPage()" class="btn btn-secondary">Anterior</button>
            <button id="nextPageBtn" onclick="goToNextPage()" class="btn btn-secondary">Próximo</button>
        </div>
        
        <hr class="my-5">
    
        <div class="card p-3 mb-4 shadow-sm">
            <h2 class="card-title text-primary">Adicionar Nova Transação</h2>
            <div class="mb-3">
                <label for="postDescription" class="form-label">Descrição:</label>
                <input type="text" id="postDescription" class="form-control" placeholder="Ex: Compra de Supermercado" required="required">
            </div>
            <div class="mb-3">
                <label for="postValue" class="form-label">Valor:</label>
                <input type="number" id="postValue" class="form-control" placeholder="Ex: 150.75" step="0.01" required="required">
            </div>
            <div class="mb-3">
                <label for="postType" class="form-label">Tipo:</label>
                <select id="postType" class="form-select">
                    <option value="despesa">despesa</option>
                    <option value="receita">receita</option>
                </select>
            </div>
            <div class="mb-3">
                <label for="postCategory" class="form-label">Categoria:</label>
                <select id="postCategory" class="form-select">
                    <option value="Alimentação">Alimentação </option>
                    <option value="Moradia">Moradia</option>
                    <option value="Lazer">Lazer</option>
                    <option value="Contas">Contas</option>
                    <option value="Saúde">Saúde</option>
                    <option value="Serviços">Serviços</option>
                    <option value="Transferências">Transferências</option>
                    <option value="Transporte">Transporte</option>
                    <option value="Investimentos">Investimentos</option>
                    <option value="Salário">Salário</option>
                    <option value="Não essenciais">Não essenciais</option>
                </select>
            </div>
            <button onclick="addTransaction()" class="btn btn-success">Adicionar Transação</button>
            <div id="postOutput" class="mt-3">
                <pre id="postJson"></pre>
            </div>
        </div>
    
        <hr class="my-5">
    
        <div class="card p-3 mb-4 shadow-sm">
			<h2 id="editarTitulo" class="card-title text-primary">Editar
				Transação</h2>
			<div class="mb-3">
                <label for="putId" class="form-label">ID da Transação:</label>
                <input type="text" id="putId" class="form-control" placeholder="ID da Transação para atualizar" required="required">
            </div>
            <div class="mb-3">
                <label for="putDescription" class="form-label">Nova Descrição:</label>
                <input type="text" id="putDescription" class="form-control" placeholder="Ex: Aluguel Atrasado" required="required">
            </div>
            <div class="mb-3">
                <label for="putValue" class="form-label">Novo Valor:</label>
                <input type="number" id="putValue" class="form-control" placeholder="Ex: 2500.00" step="0.01" required="required">
            </div>
            <div class="mb-3">
                <label for="putType" class="form-label">Novo Tipo:</label>
                <select id="putType" class="form-select">
                    <option value="despesa">despesa</option>
                    <option value="receita">receita</option>
                </select>
            </div>
            <div class="mb-3">
                <label for="putCategory" class="form-label">Nova Categoria:</label>
                <select id="putCategory" class="form-select">
                    <option value="Alimentação">Alimentação </option>
                    <option value="Moradia">Moradia</option>
                    <option value="Lazer">Lazer</option>
                    <option value="Contas">Contas</option>
                    <option value="Saúde">Saúde</option>
                    <option value="Serviços">Serviços</option>
                    <option value="Transferências">Transferências</option>
                    <option value="Transporte">Transporte</option>
                    <option value="Investimentos">Investimentos</option>
                    <option value="Salário">Salário</option>
                    <option value="Não essenciais">Não essenciais</option>
                </select>
            </div>
            <button onclick="updateTransaction()" class="btn btn-warning">Atualizar Transação</button>
            <div id="putOutput" class="mt-3">
                <pre id="putJson"></pre>
            </div>
        </div>
    
        <hr class="my-5">
    
        <div class="card p-3 mb-4 shadow-sm">
            <h2 id="deletarTransacaoTitulo" class="card-title text-primary">Deletar Transação</h2>
            <div class="mb-3">
                <label for="deleteId" class="form-label">ID da Transação:</label>
                <input type="text" id="deleteId" class="form-control" placeholder="ID da Transação para deletar">
            </div>
            <button onclick="deleteTransaction()" class="btn btn-danger">Deletar Transação</button>
            <div id="deleteOutput" class="mt-3">
                <pre id="deleteJson"></pre>
            </div>
        </div>
        
        <hr class="my-5">
        
        <div class="card p-3 mb-4 shadow-sm">
            <h2 class="card-title text-primary">Resumo Financeiro</h2>
            <p class="fs-5"><strong>Total Receitas:</strong> <span id="totalReceitas" class="text-success">R$ 0.00</span></p>
            <p class="fs-5"><strong>Total Despesas:</strong> <span id="totalDespesas" class="text-danger">R$ 0.00</span></p>
            <p class="fs-4 fw-bold"><strong>Saldo Atual:</strong> <span id="saldoAtual" class="text-info">R$ 0.00</span></p>
    
            <h3 class="mt-4 text-secondary">Despesas por Categoria:</h3>
            <ul id="despesasPorCategoriaList" class="list-group list-group-flush">
            </ul>
    
            <h3 class="mt-4 text-secondary">Receitas por Categoria:</h3>
            <ul id="receitasPorCategoriaList" class="list-group list-group-flush">
            </ul>
            <pre id="summaryJson" style="display:none;"></pre>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script src="script.js"></script> 
</body>
</html>