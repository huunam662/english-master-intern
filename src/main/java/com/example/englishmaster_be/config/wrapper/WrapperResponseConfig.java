
package com.example.englishmaster_be.config.wrapper;

import com.example.englishmaster_be.common.annotation.DefaultMessage;
import com.example.englishmaster_be.common.dto.response.FilterResponse;
import com.example.englishmaster_be.common.dto.response.WrapperApiResponse;
import com.example.englishmaster_be.common.holder.DefaultMessageHolder;
import com.example.englishmaster_be.domain.file_storage.dto.response.ResourceResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.MethodParameter;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;

@RestControllerAdvice
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WrapperResponseConfig implements ResponseBodyAdvice<Object> {

    HttpServletRequest httpServletRequest;

    @Override
    public boolean supports(
            @NonNull MethodParameter returnType,
            @NonNull Class<? extends HttpMessageConverter<?>> converterType
    ) {

        // (1) -> Nhận biết hướng đi của request và vị trí của handler response
        String requestURI = httpServletRequest.getRequestURI();
        String packageName = returnType.getContainingClass().getPackageName();
        Class<?> declaringClass = returnType.getDeclaringClass();

        System.out.println("----------- supports ResponseBodyAdvice -----------");
        System.out.println("requestURI: " + requestURI);
        System.out.println("packageName: " + packageName);
        System.out.println("declaringClass: " + declaringClass);
        System.out.println("annotation declaringClass: " + declaringClass.getAnnotation(RestController.class));
        System.out.println("----------- supports ResponseBodyAdvice -----------");
        // -> end (1)

        // -> Cho phép beforeBodyWrite nhận xử lý nếu thỏa điều kiện dưới đây
        return !requestURI.contains("/v3/api-docs")
                && !packageName.contains("org.springdoc.webmvc")
                && !declaringClass.getPackageName().contains("org.springdoc.webmvc")
                && (
                    declaringClass.getAnnotation(RestController.class) != null
                    ||
                    returnType.getParameterType().equals(WrapperApiResponse.class)
                    ||
                    returnType.getParameterType().equals(WrapperApiResponse.ExceptionResponse.class)
                );
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            @NonNull MethodParameter returnType,
            @NonNull MediaType selectedContentType,
            @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
            @NonNull ServerHttpRequest request,
            @NonNull ServerHttpResponse response
    ) {

        System.out.println("----------- beforeBodyWrite -----------");
        System.out.println(returnType.getContainingClass().getPackageName());
        if(body != null) System.out.println(body.getClass().getSimpleName());
        System.out.println(returnType.getMethod());
        System.out.println("----------- beforeBodyWrite -----------");

        if(body instanceof WrapperApiResponse.ExceptionResponse exceptionResponseModel) {

            response.setStatusCode(exceptionResponseModel.getStatus());

            exceptionResponseModel.setSuccess(Boolean.FALSE);
            exceptionResponseModel.setPath(request.getURI().getPath());

            return exceptionResponseModel;
        }

        HttpStatus statusResponse = status4Response(request, returnType);

        if(body instanceof ResourceResponse resourceResponse) {

            String typeLoad = resourceResponse.getTypeLoad().name().toLowerCase();

            response.getHeaders().set(HttpHeaders.CONTENT_TYPE, resourceResponse.getContentType());
            response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, typeLoad + "; filename=\"" + resourceResponse.getFileName() + "\"");
            response.getHeaders().setContentLength(resourceResponse.getContentLength());

            return resourceResponse.getResource();
        }

        if(body instanceof WrapperApiResponse responseModel)
            body = responseModel.getResponseData();

        else if(body instanceof ResponseEntity<?> responseEntity)
            body = responseEntity.getBody();

        else if(body instanceof FilterResponse<?> filterResponse){

            if(filterResponse.getContent() != null)
                filterResponse.setContentLength(filterResponse.getContent().size());

            filterResponse.withPreviousAndNextPage();
        }

        String messageResponse = message4Response(returnType);

        return WrapperApiResponse.builder()
                .success(Boolean.TRUE)
                .status(statusResponse)
                .code(statusResponse.value())
                .message(messageResponse)
                .path(request.getURI().getPath())
                .responseData(body)
                .build();
    }


    private HttpStatus status4Response(
            @NonNull ServerHttpRequest request,
            @NonNull MethodParameter returnType
    ) {

        Method method = returnType.getMethod();

        if(method != null){
            ResponseStatus responseStatusAnnotation = method.getAnnotation(ResponseStatus.class);
            if(responseStatusAnnotation != null)
                return responseStatusAnnotation.value();
        }

        HttpMethod httpMethod = request.getMethod();

        if(httpMethod.equals(HttpMethod.POST))
            return HttpStatus.CREATED;
        else if(httpMethod.equals(HttpMethod.PUT) || httpMethod.equals(HttpMethod.PATCH))
            return HttpStatus.ACCEPTED;
        else if(httpMethod.equals(HttpMethod.DELETE))
            return HttpStatus.NO_CONTENT;
        else return HttpStatus.OK;
    }

    private String message4Response(@NonNull MethodParameter returnType){

        Method method = returnType.getMethod();

        if(method != null){

            DefaultMessage defaultMessage = method.getAnnotation(DefaultMessage.class);

            if(defaultMessage != null) return defaultMessage.value();
        }

        return DefaultMessageHolder.getMessage();
    }
}
