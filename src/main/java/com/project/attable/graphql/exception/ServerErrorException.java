package com.project.attable.graphql.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;

public class ServerErrorException extends RuntimeException implements GraphQLError {

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    public ServerErrorException(String errorMsg) {
        super(errorMsg);
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.ExecutionAborted;
    }

    @Override
    @JsonIgnore
    public StackTraceElement[] getStackTrace() {
        return super.getStackTrace();
    }
}
