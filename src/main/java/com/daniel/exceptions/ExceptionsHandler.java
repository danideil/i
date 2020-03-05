package com.daniel.exceptions;

import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.daniel.beans.ErrorBean;
import com.daniel.enums.ErrorTypes;

// Exception handler class
@RestControllerAdvice
public class ExceptionsHandler {

	//	Response - Object in Spring
	@ExceptionHandler
	@ResponseBody
	// Variable name is throwable in order to remember that it handles Exception and Error
	public ErrorBean toResponse(Throwable throwable, HttpServletResponse response) {

		//	ErrorBean errorBean;
		if(throwable instanceof ApplicationException) {
			//response.setStatus(650);
			ApplicationException appException = (ApplicationException) throwable;
		
			ErrorTypes errorType = appException.getErrorType(); 
			int errorNumber = errorType.getErrorNumber();
			String errorMessage = errorType.getErrorMessage();
			String errorName = errorType.getErrorName();
			boolean isShowStackTrace = errorType.isShowStackTrace();
			ErrorBean errorBean = new ErrorBean(errorNumber, errorMessage, errorName, isShowStackTrace);
			response.setStatus(errorNumber);
			if(isShowStackTrace) {
				appException.printStackTrace();
			}

			return errorBean;
		}

		response.setStatus(600);

		String errorMessage = throwable.getMessage();
		ErrorBean errorBean = new ErrorBean(601, errorMessage, "General error", false);
		throwable.printStackTrace();
		return errorBean;
	}

}

