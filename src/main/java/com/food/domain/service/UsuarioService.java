package com.food.domain.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.food.api.model.input.SenhaInput;
import com.food.domain.exception.EntidadeEmUsoException;
import com.food.domain.exception.NegocioException;
import com.food.domain.exception.UsuarioNaoEncontradoException;
import com.food.domain.model.Grupo;
import com.food.domain.model.Usuario;
import com.food.domain.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	private static final String MSG_USUARIO_EM_USO = "UsuarioInput de código %d não pode ser removida, pois está em uso";
	private static final String MSG_SENHA_VALIDA = "A senha está incorreta";
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private GrupoService grupoService;

	@Transactional
	public Usuario salvar(Usuario usuario) {
		usuarioRepository.detach(usuario);
		
		Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
		
		if(usuarioExistente.isPresent() && usuarioExistente.get().equals(usuario)) {
			throw new NegocioException(
					String.format("Já existe usuário com este e-mail", usuario.getEmail()));
		}
		return usuarioRepository.save(usuario);
	}

	@Transactional
	public void excluir(Long usuarioId)  {
		try {
			usuarioRepository.deleteById(usuarioId);
			usuarioRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new UsuarioNaoEncontradoException(usuarioId);
		}catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_USUARIO_EM_USO, usuarioId));
		}
	}
	
	public Usuario buscarOuFalhar(Long usuarioId) {
		return usuarioRepository.findById(usuarioId)
				.orElseThrow( () -> new UsuarioNaoEncontradoException(usuarioId));
	}

	@Transactional
	public Usuario atualizarSenha(Long usuarioId, @Valid SenhaInput senhaInput) {
		try {
			Usuario usuario = buscarOuFalhar(usuarioId);
			
			if(!usuario.getSenha().equals(senhaInput.getSenhaAtual())){
				throw new NegocioException(MSG_SENHA_VALIDA);
			}
			usuario.setSenha(senhaInput.getSenhaAtual());
			usuarioRepository.flush();
			return usuario;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

	@Transactional
	public void desassociarFormaPagamento(Long usuarioId, Long grupoId) {
		Usuario usuario = buscarOuFalhar(usuarioId);
		Grupo grupo = grupoService.buscarOuFalhar(grupoId);
		usuario.getGrupos().remove(grupo);
	}

	@Transactional
	public void associarFormaPagamento(Long usuarioId, Long grupoId) {
		try {
			Usuario usuario = buscarOuFalhar(usuarioId);
			Grupo grupo = grupoService.buscarOuFalhar(grupoId);
			usuario.getGrupos().add(grupo);
		} catch (DataIntegrityViolationException e) {
			throw new NegocioException(MSG_SENHA_VALIDA);
		} catch (Exception e) {
			throw new NegocioException("Erro genérico:" + e.getMessage());
		}
		
	}

}
