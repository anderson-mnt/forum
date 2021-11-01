package br.com.alura.forum.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;

import br.com.alura.forum.modelo.Topico;

public class TopicoDTO {
	
	private Long id;
	private String titulo;
	private String mensagem;
	private LocalDateTime dataCriacao;
	
	public TopicoDTO(Topico topico) {
		this.id = topico.getId();
		this.titulo = topico.getTitulo();
		this.mensagem = topico.getMensagem();
		this.dataCriacao = topico.getDataCriacao();
	}
	
	public Long getId() {
		return id;
	}
	public String getTitulo() {
		return titulo;
	}
	public String getMensagem() {
		return mensagem;
	}
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public static Page<TopicoDTO> converter(Page<Topico> topicos) {
		// pega lista de topicos, pra cada topico da new no topicoDto, guarda na lista de topicoDto e devolve a lista 
		return topicos.map(TopicoDTO::new);
	}
}
