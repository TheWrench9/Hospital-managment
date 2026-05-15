package com.hospital.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(String message, T data) {
        ApiResponse<T> resp = new ApiResponse<>();
        resp.setSuccess(true);
        resp.setMessage(message);
        resp.setData(data);
        return resp;
    }

    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> resp = new ApiResponse<>();
        resp.setSuccess(true);
        resp.setMessage("Success");
        resp.setData(data);
        return resp;
    }

    public static <T> ApiResponse<T> error(String message) {
        ApiResponse<T> resp = new ApiResponse<>();
        resp.setSuccess(false);
        resp.setMessage(message);
        return resp;
    }
}
