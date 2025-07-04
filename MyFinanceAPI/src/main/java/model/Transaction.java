package model;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;

public class Transaction {
	private int id;
	private String descricao;
	private double valor;
	private String tipo;
	private String categoria;
	private String data_criacao;
	
	public Transaction() {}

	public Transaction(int id, String descricao, double valor, String tipo, String categoria, String data_criacao) {
		this.id = id;
		this.descricao = descricao;
		this.valor = valor;
		this.tipo = tipo;
		this.categoria = categoria;
		this.data_criacao = data_criacao;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getData_criacao() {
		return data_criacao;
	}

	public void setData_criacao(String data_criacao) {
		this.data_criacao = data_criacao;
	}
	
	
}
