package com.project.doubt_solver.exceptions;

public class ResourceNotFoundException extends RuntimeException{

    private String resourceName;
    private String fieldName;
    private int fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, int fieldValue){
         super(resourceName+" resource with "+fieldName+" : "+fieldValue+" not found");
         this.resourceName=resourceName;
         this.fieldName=fieldName;
         this.fieldValue=fieldValue;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public int getFieldValue() {
        return fieldValue;
    }
}
