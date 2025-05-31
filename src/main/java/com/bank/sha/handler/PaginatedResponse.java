package com.bank.sha.handler;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedResponse<T> {
    private HttpStatus status;
    private String message;
    private List<T> data;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

}
