package online.switcheroos.accounts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
public class ExceptionResponse {

    private final int status;

    private final String message;

    private final String detail;

    private final String path;

    @JsonProperty
    private final String timestamp;

    public ExceptionResponse(int status, String message, String detail, String path) {
        this.status = status;
        this.message = message;
        this.detail = detail;
        this.path = path;
        this.timestamp = setTimestamp();
    }

    private String setTimestamp() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }
}
