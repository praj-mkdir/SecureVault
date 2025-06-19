package com.praj.secureVault.util.response;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
public class ApiErrorResponse {
    private int status;
    private String message;
    private Instant timestamp;
    private String traceId;
    private String path;

    // Constructor private to force use of builder
    private ApiErrorResponse(Builder builder) {
        this.status = builder.status;
        this.message = builder.message;
        this.timestamp = builder.timestamp;
        this.traceId = builder.traceId;
        this.path = builder.path;
    }

    //Just practicing the builder desing pattern
    public static class Builder {
        private int status;
        private String message;
        private Instant timestamp;
        private String traceId;
        private String path;

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder traceId(String traceId) {
            this.traceId = traceId;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public ApiErrorResponse build() {
            return new ApiErrorResponse(this);
        }

    }

 //

}
