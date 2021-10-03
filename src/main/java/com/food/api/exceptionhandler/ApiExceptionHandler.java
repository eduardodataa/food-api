package com.food.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
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
	
	private static final String ERRO_INTERNO_INSPERADO_NO_SISTEMA_ENTRE_EM_CONTATO_COM_O_ADMINISTRADOR_DO_SISTEMA = "Ocorreu um erro interno insperado no sistema. Entre em contato com o administrador do sistema";

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
			return handlePropertyBinding((PropertyBindingException)rootCause, headers, status, request, detailMessage, problemType);
		}

		//senão trata de maneira genérica
		String detail = "Corpo da requisição inválido. Verifique erro de sintaxe";
		Problem problem = createProblemBuilder(status, problemType, detail, detail).build();

		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	/**
	 * Trata erros na conversão de parâmetros passados na URL
	 */
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;
		if(ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatch(ex, headers, status, request, problemType);
		}
		
		return super.handleTypeMismatch(ex, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		String detailMessage = "O recurso '%s' que você tentou acessar é inexistente";
		String detail = String.format(detailMessage, ex.getRequestURL());
		Problem problem = createProblemBuilder(status, problemType, detail, detail).build();
		return handleExceptionInternal(ex, problem, headers, status, request);
		
//		return super.handleNoHandlerFoundException(ex, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ProblemType problemType = ProblemType.DADOS_INVALIDOS;
		String detailMessage = "Um ou campos estão inválidos. Faça o preenchimento correto e tente novamente.";
		
		BindingResult bindingResult = ex.getBindingResult();
		List<Problem.Field> problemFields = bindingResult.getFieldErrors().stream()
				.map(fieldErro -> Problem.Field.builder()
						.name(fieldErro.getField())
						.userMessage(fieldErro.getDefaultMessage())
						.build())
				.collect(Collectors.toList());
		
		Problem problem = createProblemBuilder(status, problemType, detailMessage, detailMessage)
				.fields(problemFields)
				.build();
		return handleExceptionInternal(ex, problem, headers, status, request);
		
	}
	
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {


		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ProblemType problemType = ProblemType.ERRO_INTERNO_SISTEMA;
		String detail = ERRO_INTERNO_INSPERADO_NO_SISTEMA_ENTRE_EM_CONTATO_COM_O_ADMINISTRADOR_DO_SISTEMA;
		ex.printStackTrace();
		Problem problem = createProblemBuilder(status, problemType, detail, detail).build();
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
		
//		return super.handleHttpMessageNotWritable(ex, headers, status, request);
	}
	

	/**
	 * Monta o objeto tratado para o retorno de parâmetro inválido
	 * 
	 * @param ex
	 * @param headers
	 * @param status
	 * @param request
	 * @param problemType
	 * @return
	 */
	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request, ProblemType problemType) {
		
		MethodArgumentTypeMismatchException ee = (MethodArgumentTypeMismatchException) ex;
		String detailMessage = "O parâmetro de URL '%s' recebeu o valor '%s', "
		        + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo '%s'.";
		String detail = String.format(detailMessage, ee.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
		Problem problem = createProblemBuilder(status, problemType, detail, ERRO_INTERNO_INSPERADO_NO_SISTEMA_ENTRE_EM_CONTATO_COM_O_ADMINISTRADOR_DO_SISTEMA)
				.build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	/**
	 * monta o objeto quando se tenta utilizar atributos anotados com @JsonIgnore
	 * @param ex
	 * @param headers
	 * @param status
	 * @param request
	 * @param format
	 * @param problemType
	 * @return
	 */
	private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request, String format, ProblemType problemType) {

		String path = getPathFromException(ex.getPath());
		String detail = String.format(format, path);
		Problem problem = createProblemBuilder(status, problemType, detail, ERRO_INTERNO_INSPERADO_NO_SISTEMA_ENTRE_EM_CONTATO_COM_O_ADMINISTRADOR_DO_SISTEMA)
				.build();

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
		Problem problem = createProblemBuilder(status, problemType, detail, detail).build();

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
		ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
		String detail = e.getMessage();
		
		Problem problem = createProblemBuilder(status, problemType, detail, detail).build();
		
		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(NegocioException.class)
	public ResponseEntity<?> handleNegocioException(NegocioException e, WebRequest request){
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ProblemType problemType = ProblemType.ERRO_NEGOCIO;
		String detail = e.getMessage();
		Problem problem = createProblemBuilder(status, problemType, detail, detail).build();
		
		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(EntidadeEmUsoException.class)
	public ResponseEntity<?> handleEntidadeEmUsoException(EntidadeEmUsoException e, WebRequest request){

		HttpStatus status = HttpStatus.CONFLICT;
		ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
		String detail = e.getMessage();
		Problem problem = createProblemBuilder(status, problemType, detail, detail).build();
		
		return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		if(body ==  null) {
			body = Problem.builder()
					.title(status.getReasonPhrase())
					.status(status.value())
					.timestamp(OffsetDateTime.now())
					.userMessage(ERRO_INTERNO_INSPERADO_NO_SISTEMA_ENTRE_EM_CONTATO_COM_O_ADMINISTRADOR_DO_SISTEMA)
					.build();
		}else {
			if(body instanceof String) {
				body = Problem.builder()
						.title((String) body)
						.status(status.value())
						.timestamp(OffsetDateTime.now())
						.userMessage(ERRO_INTERNO_INSPERADO_NO_SISTEMA_ENTRE_EM_CONTATO_COM_O_ADMINISTRADOR_DO_SISTEMA)
						.build();
			}
		}
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType type, String detail, String userMessage) {
		
		return Problem.builder()
				.status(status.value())
				.type(type.getUri())
				.title(type.getTitle())
				.detail(detail)
				.timestamp(OffsetDateTime.now())
				.userMessage(userMessage);
		
	}

}
