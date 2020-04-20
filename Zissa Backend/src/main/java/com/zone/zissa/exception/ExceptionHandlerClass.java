package com.zone.zissa.exception;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.zone.zissa.response.AttrServiceResponse;
import com.zone.zissa.response.PageServiceResponse;
import com.zone.zissa.response.ServiceResponse;

/**
 * Controller responsible for handling Exceptions
 */
@ControllerAdvice
public class ExceptionHandlerClass {

    /**
     * this is constructor
     */
    private ExceptionHandlerClass() {

    }

    /**
     * Responsible for handling BadRequestException globally.
     *
     * @return ServiceResponse
     */
    @ExceptionHandler(BadRequestException.class)
    public static ServiceResponse handle(BadRequestException ex) {

        int status = HttpServletResponse.SC_BAD_REQUEST;
        return handle(ex, status);

    }

    /**
     * Responsible for handling AccessDeniedException globally.
     *
     * @return ServiceResponse
     */
    @ExceptionHandler(AccessDeniedException.class)
    public static ServiceResponse handle(AccessDeniedException ex) {

        int status = HttpServletResponse.SC_FORBIDDEN;
        return handle(ex, status);
    }

    /**
     * Responsible for handling NotFoundException globally.
     *
     * @return ServiceResponse
     */
    @ExceptionHandler(NotFoundException.class)
    public static ServiceResponse handle(NotFoundException ex) {

        int status = HttpServletResponse.SC_NOT_FOUND;
        return handle(ex, status);

    }

    /**
     * Responsible for handling ConflictException globally.
     *
     * @return ServiceResponse
     */
    @ExceptionHandler(ConflictException.class)
    public static ServiceResponse handle(ConflictException ex) {

        int status = HttpServletResponse.SC_CONFLICT;
        return handle(ex, status);

    }

    /**
     * Responsible for handling NoContentException globally.
     *
     * @return ServiceResponse
     */
    @ExceptionHandler(NoContentException.class)
    public static ServiceResponse handle(NoContentException ex) {

        int status = HttpServletResponse.SC_NO_CONTENT;
        return handle(ex, status);

    }

    /**
     * Responsible for handling SuccessException globally.
     *
     * @return ServiceResponse
     */
    @ExceptionHandler(DataNotFoundException.class)
    public static ServiceResponse handle(DataNotFoundException ex) {

        int status = HttpServletResponse.SC_OK;
        return handle(ex, status);

    }

    /**
     * Responsible for handling SuccessException globally.
     *
     * @return ServiceResponse
     */
    @ExceptionHandler(DataToLongException.class)
    public static ServiceResponse handle(DataToLongException ex) {

        int status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        return handle(ex, status);

    }

    /**
     * handle method
     * 
     * @param ex
     * @param status
     *
     * @return ServiceResponse
     */
    private static ServiceResponse handle(CustomException ex, int status) {
        ServiceResponse response = new ServiceResponse();

        response.setStatus(status);
        response.setMessage(ex.getMessage());

        return response;
    }

    /**
     * handleServiceResponse method
     * 
     * @param status
     * @param message
     *
     * @return ServiceResponse
     */
    public static ServiceResponse handleServiceResponse(int status, String message) {
        ServiceResponse response = new ServiceResponse();

        response.setStatus(status);
        response.setMessage(message);

        return response;
    }

    /**
     * handleAttributeResponse method
     * 
     * @param status
     * @param message
     *
     * @return AttrServiceResponse
     */
    public static AttrServiceResponse handleAttributeResponse(int status, String message) {
        AttrServiceResponse response = new AttrServiceResponse();

        response.setStatus(status);
        response.setMessage(message);

        return response;
    }

    /**
     * handlePageServiceResponse method
     * 
     * @param status
     * @param message
     *
     * @return PageServiceResponse
     */
    public static PageServiceResponse handlePageServiceResponse(int status, String message) {
        PageServiceResponse response = new PageServiceResponse();

        response.setStatus(status);
        response.setMessage(message);

        return response;
    }

}
