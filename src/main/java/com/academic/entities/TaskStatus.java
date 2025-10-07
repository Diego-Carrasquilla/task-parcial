package com.academic.entities;
public enum TaskStatus {
    PENDING("Pendiente"),
    SUBMITTED("Entregada");
    
    private final String displayName;
    
    TaskStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
