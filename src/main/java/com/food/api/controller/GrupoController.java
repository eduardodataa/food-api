package com.food.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.food.api.model.GrupoDTO;
import com.food.domain.model.Grupo;
import com.food.domain.repository.GrupoRepository;
import com.food.domain.service.CadastroGrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private CadastroGrupoService cadastroGrupoService;

	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping
	public List<GrupoDTO> listar(){
		return gruposToGrupoDTO(grupoRepository.findAll());
	}
	
	//atualizar grupo
	@PutMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.OK)
	public GrupoDTO atualizar(@PathVariable Long grupoId, @RequestBody @Valid Grupo grupo) {
		Grupo grupoAtual = cadastroGrupoService.buscarOuFalhar(grupoId);
		BeanUtils.copyProperties(grupo,grupoAtual , "id");
		return grupoToGrupoDTO( cadastroGrupoService.salvar(grupoAtual));
	}
	

	private List<GrupoDTO> gruposToGrupoDTO(List<Grupo> listaGrupos) {
		return listaGrupos.stream().map(grupo -> grupoToGrupoDTO(grupo)).collect(Collectors.toList());
	}

	private GrupoDTO grupoToGrupoDTO(Grupo cidade) {
		return modelMapper.map(cidade, GrupoDTO.class);
	}

	
	@PostMapping
	public ResponseEntity<?> salvar(@RequestBody Grupo grupo) {
		try {
			grupo = cadastroGrupoService.salvar(grupo);
			return ResponseEntity.status(HttpStatus.CREATED).body(grupo);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Erro ao salvar: " + e.getMessage());
		}
	}
	
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long grupoId){
		cadastroGrupoService.excluir(grupoId);
	}
}
