package model;

import java.util.Map;

public class ResumoFinanceiro {
	
	private double totalReceitas;
    private double totalDespesas;
    private double saldoAtual;
    private Map<String, Double> despesasPorCategoria;
    private Map<String, Double> receitasPorCategoria;

	public ResumoFinanceiro() {}

	public ResumoFinanceiro(double totalReceitas, double totalDespesas, double saldoAtual,
			Map<String, Double> despesasPorCategoria, Map<String, Double> receitasPorCategoria) {
		this.totalReceitas = totalReceitas;
		this.totalDespesas = totalDespesas;
		this.saldoAtual = saldoAtual;
		this.despesasPorCategoria = despesasPorCategoria;
		this.receitasPorCategoria = receitasPorCategoria;
	}

	public double getTotalReceitas() {
		return totalReceitas;
	}

	public void setTotalReceitas(double totalReceitas) {
		this.totalReceitas = totalReceitas;
	}

	public double getTotalDespesas() {
		return totalDespesas;
	}

	public void setTotalDespesas(double totalDespesas) {
		this.totalDespesas = totalDespesas;
	}

	public double getSaldoAtual() {
		return saldoAtual;
	}

	public void setSaldoAtual(double saldoAtual) {
		this.saldoAtual = saldoAtual;
	}

	public Map<String, Double> getDespesasPorCategoria() {
		return despesasPorCategoria;
	}

	public void setDespesasPorCategoria(Map<String, Double> despesasPorCategoria) {
		this.despesasPorCategoria = despesasPorCategoria;
	}

	public Map<String, Double> getReceitasPorCategoria() {
		return receitasPorCategoria;
	}

	public void setReceitasPorCategoria(Map<String, Double> receitasPorCategoria) {
		this.receitasPorCategoria = receitasPorCategoria;
	}
}
