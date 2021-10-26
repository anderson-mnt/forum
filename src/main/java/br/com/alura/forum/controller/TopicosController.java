package br.com.alura.forum.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.form.AtualizacaoTopicoForm;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.controller.repository.CursoRepository;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.TopicoRepository;

@RestController //avisa o spring que por padrão o controler é um restcontroller e nao precisa usar a anotacao @ResponseBody
@RequestMapping("/topicos")
public class TopicosController {
	
	@Autowired //injeção de dependencia
	private TopicoRepository topicoRepository;
	
	@Autowired 
	private CursoRepository cursoRepository;
	
	//@ResponseBody //diz ao spring para pegar o retorno do metodo e devolver para o navegador
	@GetMapping 
	public List<TopicoDTO> lista(String nomeCurso){//nomeCurso é o parametro enviado pela url do navegador
		if(nomeCurso == null) {
			List<Topico> topicos = topicoRepository.findAll();
			return TopicoDTO.converter(topicos);
		} else {
			List<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso);
			return TopicoDTO.converter(topicos);
		}
		
	}
	
	@PostMapping 
	@Transactional
	public ResponseEntity<TopicoDTO> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {//@RequestBody avisa que o parametro vai vir do corpo da requisicao
		Topico topico = form.converter(cursoRepository);
		topicoRepository.save(topico);
		
		URI uri =  uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDTO(topico)); //retorna codigo 201 de novo recurso criado no servidor
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DetalhesDoTopicoDTO> detalhar(@PathVariable Long id) {
		Optional<Topico> topico = topicoRepository.findById(id);
		
		if(topico.isPresent()) {
			return ResponseEntity.ok(new DetalhesDoTopicoDTO(topico.get()));
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	@PutMapping("/{id}")
	@Transactional //avisa o spring que é pra commitar a transacao no final
	public ResponseEntity<TopicoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form){
			
		Optional<Topico> optional = topicoRepository.findById(id);
		
		if(optional.isPresent()) {
			
			
			Topico topico = form.atualizar(id, topicoRepository);
			
			return ResponseEntity.ok(new TopicoDTO(topico));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<TopicoDTO> remover(@PathVariable Long id){
		Optional<Topico> optional = topicoRepository.findById(id);
		
		if(optional.isPresent()) {
			topicoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
	
}
