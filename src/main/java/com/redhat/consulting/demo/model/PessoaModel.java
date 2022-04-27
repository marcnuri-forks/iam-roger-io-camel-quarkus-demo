package com.redhat.consulting.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "tb_pessoa")
@NamedQuery(name = "findAll", query = "select a from PessoaModel a")
public class PessoaModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5481721059327918490L;
	
    @Id
    @Column(name="pessoaID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
    @Column(name="nome", nullable = false)
    private String nome;
	
    @Column(name="cpf_cnpj", nullable = false)
    private String cpf;
    
    @Column(name="email", nullable = true)
    private String email;
	
    
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}	
	
}
