package com.techbro.sammychatbot.commons;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomResponse {

    private Boolean error;
    private Object data;
    private String message;
    private List<String> validationErrors;
    private PaginationInfo paginationInfo;




    public static ResponseEntity<?> bake(String errorMessage,int statusCode){
        var response = CustomResponse.builder().error(statusCode >= 400).message(errorMessage).build();
        return ResponseEntity.status(statusCode).body(response);
    }
    public static ResponseEntity<?> bake(String message,int statusCode,Object data){
        var response = CustomResponse.builder().error(statusCode >= 400).message(message).data(data).build();
        return ResponseEntity.status(statusCode).body(response);
    }

    public static ResponseEntity<?> bake(String message,int statusCode,List<String> validationErrors){
        var response = CustomResponse.builder().error(statusCode >= 400).message(message).validationErrors(validationErrors).build();
        return ResponseEntity.status(statusCode).body(response);
    }

    public static ResponseEntity<?> paginate(String message, int statusCode, Object data, Page<?> pagination){
        var paginationInfo = PaginationInfo.builder()
                .pageNum(pagination.getNumber())
                .totalPages(pagination.getTotalPages())
                .pageItemCount(pagination.getNumberOfElements())
                .totalElement((int) pagination.getTotalElements())
                .build();

        var response = CustomResponse.builder().error(statusCode >= 400).message(message).data(data).paginationInfo(paginationInfo).build();
        return ResponseEntity.status(statusCode).body(response);
    }


    @Builder
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class PaginationInfo {
        private int pageNum;
        private int totalPages;
        private int pageItemCount;
        private int totalElement;
    }
}
