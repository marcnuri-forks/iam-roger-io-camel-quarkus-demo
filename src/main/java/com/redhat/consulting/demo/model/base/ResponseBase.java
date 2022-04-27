package com.redhat.consulting.demo.model.base;

import java.io.Serializable;

public class ResponseBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6546441277764127068L;
	
	
	private String mensagem;

	public ResponseBase(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getMensagem() {
		return mensagem;
	}


	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	
}
