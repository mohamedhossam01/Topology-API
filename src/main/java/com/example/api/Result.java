// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: https://pvs-studio.com
package com.example.api;

import java.util.Objects;

/**
 * Result class for the API that contains a boolean status and detailed message.
 * @author Mohamed Hossam
 * @version 1.0
 */
public class Result {
    /**
     * Status of the operation.
     */
    private final boolean status;
    /**
     * Detailed description of operation.
     */
    private final String message;

    /**
     * Constructor for Result object
     * @param status true if operation was successful, false otherwise
     * @param message status of operation <br>
     *                (e.g. "Successfully deleted topology")
     */
    public Result(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    /**
     * @return Status of operation
     */
    public boolean getStatus() {
        return status;
    }

    /**
     * @return Detailed description of operation.
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param result Result object to compare with
     * @return true if both objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object result) {
        if (this == result) return true;
        if (result == null || getClass() != result.getClass()) return false;

        Result that = (Result) result;

        if (status != that.status) return false;
        return Objects.equals(message, that.message);
    }

    /**
     * @return hash code of the object computed by {@link java.util.Objects Objects} class
     */
    @Override
    public int hashCode() {
        return Objects.hash(status, message);
    }
}
