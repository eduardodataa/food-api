package com.food.api.exceptionhandler;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.food.domain.exception.EntidadeEmUsoException;
import com.food.domain.exception.EntidadeNaoEncontradaException;
import com.food.domain.exception.NegocioException;

	

/**
 * exceções serão tratadas aqui por conta do @ControllerAdvice 
 * @author duduc
 *
 */
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{
	
	/**
	 * método que trata mensagens não compreendidas pela API
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		//do pacote Apache Commons Lang - pega a causa raiz da pilha de exceção.
		Throwable rootCause = ExceptionUtils.getRootCause(ex);

		ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
		//trata de maneira especializada esta exception
		if(rootCause instanceof InvalidFormatException) {
			String detailMessage = "A propriedade '%s' recebeu o valor '%s', que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.";
			return handleInvalidFormatException((InvalidFormatException)rootCause, headers, status, request, detailMessage, problemType);
		}

		if(rootCause instanceof PropertyBindingException) {
			String detailMessage = "A propriedade '%s' não existe. Favor remova-a da requisição";
			return handleIgnoredPropertyException((PropertyBindingException)rootCause, headers, status, request, detailMessage, problemType);
		}

		//senão trata de maneira genérica
		String detail = "Corpo da requisição inválido. Verifique erro de sintaxe";
		Problem problem = createProblemBuilder(status, problemType, detail).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;
		if(ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatch(ex, headers, status, request, problemType);
		}
		
		return super.handleTypeMismatch(ex, headers, status, request);
	}

	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request, ProblemType problemType) {
		
		MethodArgumentTypeMismatchException ee = (MethodArgumentTypeMismatchException) ex;
		String detailMessage = "O parâmetro de URL '%s' recebeu o valor '%s', "
		        + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo '%s'.";
		String detail = String.format(detailMessage, ee.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	

	private ResponseEntity<Object> handleIgnoredPropertyException(PropertyBindingException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request, String format, ProblemType problemType) {

		String path = getPathFromException(ex.getPath());
		String detail = String.format(format, path);
		Problem problem = createProblemBuilder(status, problemType, detail).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
		
	}

	/**
	 * retorna o caminho do atributo seja ele simples (1 nível) ou composto (2 ou mais níveis, ex.: pessoa.endereco.cidade.estado...)
	 * @param ex
	 * @return
	 */
	private String getPathFromException(List<Reference> references) {
		return references.stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));
	}
	
	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request, String detailMessage, ProblemType problemType) {

		String path = getPathFromException(ex.getPath());
		String detail = String.format(detailMessage, 
				path, 
				ex.getValue(), 
				ex.getTargetType().getSimpleName());
		Problem problem = createProblemBuilder(status, problemType, detail).build();

		return handleExceptionInternal(ex, problem, headers, status, request);
	}

	/**
	 * manipula a exceção para utilizar uma classe com campos customizados para retornar no erro da resposta
	 * @param e
	 * @return
	 */
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e, WebRequest request){
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		ProblemType problemType = ProblemType.ENTIDADE_NAO_ENCONTRADA;
		String detail = e.getMessage();
		
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		
		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocioException(NegocioException e, WebRequest request){
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.ERRO_NEGOCIO;
		String detail = e.getMessage();
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		
		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException e, WebRequest request){

		HttpStatus status = HttpStatus.CONFLICT;
		ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
		String detail = e.getMessage();
		Problem problem = createProblemBuilder(status, problemType, detail).build();
		
		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		if(body ==  null) {
			body = Problem.builder()
					.title(status.getReasonPhrase())
					.status(status.value())
					.build();
		}else {
			if(body instanceof String) {
				body = Problem.builder()
						.title((String) body)
						.status(status.value())
						.build();
			}
		}
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType type, String detail) {
		
		return Problem.builder()
				.status(status.value())
				.type(type.getUri())
				.title(type.getTitle())
				.detail(detail);
		
	}

}
