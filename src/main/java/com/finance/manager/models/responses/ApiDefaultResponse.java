
package com.finance.manager.models.responses;

public class ApiDefaultResponse<T> {
    private boolean success;
    private T data;
    private String message;

    public ApiDefaultResponse() {}

    public ApiDefaultResponse(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public static <T> ApiDefaultResponse<T> success(T data, String message) {
        return new ApiDefaultResponse<>(true, data, message);
    }

    public static <T> ApiDefaultResponse<T> error(String message) {
        return new ApiDefaultResponse<>(false, null, message);
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
