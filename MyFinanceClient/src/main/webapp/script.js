const API_BASE_URL = 'http://localhost:8080/MyFinanceAPI/transactions';

function displayJson(elementId, data) {
	const outputElement = document.getElementById(elementId);
	outputElement.textContent = JSON.stringify(data, null, 2);
}

function displayTransactionsInTable(transactions) {
    const tableBody = document.getElementById('transactionsTableBody');
    tableBody.innerHTML = '';

    transactions.forEach(transaction => {
        const row = tableBody.insertRow();

        if (transaction.tipo === 'despesa') {
            row.style.backgroundColor = '#ffcccc';
        } else if (transaction.tipo === 'receita') {
            row.style.backgroundColor = '#ccffcc';
        }

        row.insertCell().textContent = transaction.id;
        row.insertCell().textContent = transaction.descricao;
        row.insertCell().textContent = transaction.valor.toFixed(2);
        row.insertCell().textContent = transaction.tipo;
        row.insertCell().textContent = transaction.categoria;
        row.insertCell().textContent = transaction.data_criacao;

        let cell = row.insertCell();
        let button = document.createElement("button");
        button.textContent = "Excluir";
        button.classList.add('btn', 'btn-danger', 'btn-sm'); 
        button.onclick = async function() {
            const id = transaction.id;
            if (!id) {
                alert('ID Inválido!');
                return;
            }

            const confirmDelete = confirm(`Tem certeza que deseja deletar a transação com ID ${id}?`);
            if (!confirmDelete) {
                return;
            }

            try {
                const response = await fetch(`${API_BASE_URL}/${id}`, {
                    method: 'DELETE'
                });

                if (!response.ok) {
                    const errorText = await response.text();
                    throw new Error(`Erro HTTP: ${response.status} - ${errorText}`);
                }

                alert('Transação deletada com sucesso!');
                getTransactions(currentPage);
                getResumo();
            } catch (error) {
                console.error('Erro ao deletar transação:', error);
                alert('Erro ao deletar transação: ' + error.message);
            }
        };
        cell.appendChild(button);
        
        let cell2 = row.insertCell();
        let button2 = document.createElement("button");
        button2.textContent = "Editar";
        button2.classList.add('btn', 'btn-warning', 'btn-sm', 'ms-2'); 
        button2.onclick = function(){
            document.getElementById('putId').value = transaction.id;
            document.getElementById('putDescription').value = transaction.descricao;
            document.getElementById('putValue').value = transaction.valor;
            document.getElementById('putType').value = transaction.tipo;
            document.getElementById('putCategory').value = transaction.categoria;
            
            setTimeout(() => {
                document.getElementById("editarTitulo").scrollIntoView({ behavior: "smooth" });
            }, 100);
        }
        cell2.appendChild(button2);
    });
}

let currentPage = 0;
let estaFiltrado = false;

// GET
async function getTransactions(page = 0) {
	currentPage = page;
	estaFiltrado = false;

	document.getElementById('nextPageBtn').disabled = false
	document.getElementById('prevPageBtn').disabled = false

	try {
		const response = await fetch(`${API_BASE_URL}/paginated?page=${currentPage}`);
		if (!response.ok) {
			throw new Error(`Erro HTTP: ${response.status}`);
		}
		const data = await response.json();
		displayTransactionsInTable(data);

		document.getElementById('prevPageBtn').disabled = (currentPage === 0);
	} catch (error) {
		console.error('Nenhuma transação encontrada:', error);
		const tableBody = document.getElementById('transactionsTableBody');
		tableBody.innerHTML = `<tr><td colspan="6" style="color: red;">Erro ao carregar transações: ${error.message}</td></tr>`;
	}
}

function goToPrevPage() {
	if (estaFiltrado == false) {
		if (currentPage > 0) {
			getTransactions(currentPage - 1);
		}
	}
}

function goToNextPage() {
	if (estaFiltrado == false) {
		getTransactions(currentPage + 1);
	}
}

// GET com filtro
async function getFilterTransactions() {
	const type = document.getElementById('filterType').value;
	const category = document.getElementById('filterCategory').value;

	if (type == "Todas" && category != "Todas") {
		estaFiltrado = true;
		try {
			const response = await fetch(`${API_BASE_URL}/categoria/${category}`);
			if (!response.ok) {
				throw new Error(`Erro HTTP: ${response.status}`);
			}
			const data = await response.json();
			displayTransactionsInTable(data);

			document.getElementById('nextPageBtn').disabled = true
			document.getElementById('prevPageBtn').disabled = true
		} catch (error) {
			console.error('Nenhuma transação encontrada:', error);
			const tableBody = document.getElementById('transactionsTableBody');
			tableBody.innerHTML = `<tr><td colspan="6" style="color: red;">Erro ao carregar transações: ${error.message}</td></tr>`;
		}
	} else if (type != "Todas" && category == "Todas") {
		estaFiltrado = true;
		try {
			const response = await fetch(`${API_BASE_URL}/tipo/${type}`);
			if (!response.ok) {
				throw new Error(`Erro HTTP: ${response.status}`);
			}
			const data = await response.json();
			displayTransactionsInTable(data);

			document.getElementById('nextPageBtn').disabled = true
			document.getElementById('prevPageBtn').disabled = true
		} catch (error) {
			console.error('Nenhuma transação encontrada:', error);
			const tableBody = document.getElementById('transactionsTableBody');
			tableBody.innerHTML = `<tr><td colspan="6" style="color: red;">Erro ao carregar transações: ${error.message}</td></tr>`;
		}
	} else if (type != "Todas" && category != "Todas") {
		estaFiltrado = true;
		try {
			const response = await fetch(`${API_BASE_URL}/categoriaetipo/${category}+${type}`);
			if (!response.ok) {
				throw new Error(`Erro HTTP: ${response.status}`);
			}
			const data = await response.json();
			displayTransactionsInTable(data);

			document.getElementById('nextPageBtn').disabled = true
			document.getElementById('prevPageBtn').disabled = true
		} catch (error) {
			console.error('Nenhuma transação encontrada:', error);
			const tableBody = document.getElementById('transactionsTableBody');
			tableBody.innerHTML = `<tr><td colspan="6" style="color: red;">Erro ao carregar transações: ${error.message}</td></tr>`;
		}
	} else {
		getTransactions(0);
	}
}

async function limparFiltragem() {
	getTransactions(0);
	document.getElementById('filterType').selectedIndex = 0;
	document.getElementById('filterCategory').selectedIndex = 0;
}

// POST
async function addTransaction() {
	const description = document.getElementById('postDescription').value;
	const value = parseFloat(document.getElementById('postValue').value);
	const type = document.getElementById('postType').value;
	const category = document.getElementById('postCategory').value;

	const today = new Date();
	const day = String(today.getDate()).padStart(2, '0');
	const month = String(today.getMonth() + 1).padStart(2, '0');
	const year = today.getFullYear();
	const date_creation = `${day}-${month}-${year}`;

	const newTransaction = {
		descricao: description,
		valor: value,
		tipo: type,
		categoria: category,
		data_criacao: date_creation
	};

	try {
		const response = await fetch(API_BASE_URL, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(newTransaction)
		});

		if (!response.ok) {
			const errorText = await response.text();
			throw new Error(`Erro HTTP: ${response.status} - ${errorText}`);
		}

		alert('Transação adicionada com sucesso!');
		getTransactions(0);
		getResumo();
		document.getElementById('postDescription').value = '';
		document.getElementById('postValue').value = '';
		document.getElementById('postCategory').value = '';
		const section = document.getElementById("titulo");
		section.scrollIntoView({ behavior: "smooth" });
	} catch (error) {
		console.error('Erro ao adicionar transação:', error);
		alert('Erro ao adicionar transação: ' + error.message);
	}
}

// PUT
async function updateTransaction() {
	const id = document.getElementById('putId').value;
	if (!id) {
		alert('Por favor, insira o ID da transação para atualizar.');
		return;
	}

	const description = document.getElementById('putDescription').value;
	const value = parseFloat(document.getElementById('putValue').value);
	const type = document.getElementById('putType').value;
	const category = document.getElementById('putCategory').value;

	const updatedTransaction = {
		descricao: description,
		valor: value,
		tipo: type,
		categoria: category,
	};

	try {
		const response = await fetch(`${API_BASE_URL}/${id}`, {
			method: 'PUT',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(updatedTransaction)
		});

		if (!response.ok) {
			const errorText = await response.text();
			throw new Error(`Erro HTTP: ${response.status} - ${errorText}`);
		}

		alert('Transação atualizada com sucesso!');
		getTransactions(0);
		getResumo();
		document.getElementById('putDescription').value = '';
		document.getElementById('putValue').value = '';
		document.getElementById('putCategory').value = '';
		const section = document.getElementById("titulo");
		section.scrollIntoView({ behavior: "smooth" });
	} catch (error) {
		console.error('Erro ao atualizar transação:', error);
		alert('Erro ao atualizar transação: ' + error.message);
	}
}

// DELETE
async function deleteTransaction() {
	const id = document.getElementById('deleteId').value;
	if (!id) {
		alert('Por favor, insira o ID da transação para deletar.');
		return;
	}

	const confirmDelete = confirm(`Tem certeza que deseja deletar a transação com ID ${id}?`);
	if (!confirmDelete) {
		return;
	}

	try {
		const response = await fetch(`${API_BASE_URL}/${id}`, {
			method: 'DELETE'
		});

		if (!response.ok) {
			const errorText = await response.text();
			throw new Error(`Erro HTTP: ${response.status} - ${errorText}`);
		}

		alert('Transação deletada com sucesso!');
		getTransactions(0);
		getResumo();
		const section = document.getElementById("titulo");
		section.scrollIntoView({ behavior: "smooth" });
	} catch (error) {
		console.error('Erro ao deletar transação:', error);
		alert('Erro ao deletar transação: ' + error.message);
	}
}

document.addEventListener('DOMContentLoaded', (event) => {
	getTransactions(0);
	getResumo();
});


// GET resumo

async function getResumo() {
	try {
		const response = await fetch(`${API_BASE_URL}/resumo`);
		if (!response.ok) {
			throw new Error(`Erro HTTP: ${response.status}`);
		}

		const resumoData = await response.json();

		document.getElementById('totalReceitas').textContent = `R$ ${resumoData.totalReceitas ? resumoData.totalReceitas.toFixed(2) : '0.00'}`;
		document.getElementById('totalDespesas').textContent = `R$ ${resumoData.totalDespesas ? resumoData.totalDespesas.toFixed(2) : '0.00'}`;

		const saldoElement = document.getElementById('saldoAtual');
		const saldo = resumoData.saldoAtual || 0;
		saldoElement.textContent = `R$ ${saldo.toFixed(2)}`;
		if (saldo >= 0) {
			saldoElement.style.color = 'green';
		} else {
			saldoElement.style.color = 'red';
		}

		const despesasList = document.getElementById('despesasPorCategoriaList');
		despesasList.innerHTML = '';
		if (resumoData.despesasPorCategoria) {
			for (const category in resumoData.despesasPorCategoria) {
				const li = document.createElement('li');
				li.textContent = `${category}: R$ ${resumoData.despesasPorCategoria[category].toFixed(2)}`;
				despesasList.appendChild(li);
			}
		} else {
			despesasList.innerHTML = '<li>Nenhuma despesa por categoria encontrada.</li>';
		}

		const receitasList = document.getElementById('receitasPorCategoriaList');
		receitasList.innerHTML = '';
		if (resumoData.receitasPorCategoria) {
			for (const category in resumoData.receitasPorCategoria) {
				const li = document.createElement('li');
				li.textContent = `${category}: R$ ${resumoData.receitasPorCategoria[category].toFixed(2)}`;
				receitasList.appendChild(li);
			}
		} else {
			receitasList.innerHTML = '<li>Nenhuma receita por categoria encontrada.</li>';
		}
	} catch (error) {
		console.error('Erro ao obter resumo financeiro:', error);
		alert('Erro ao carregar resumo financeiro: ' + error.message);
	}



}














