package com.unilab.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import com.unilab.model.Lab;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LabDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    
    @NotBlank(message = "Lab code is required")
    @Size(max = 50, message = "Lab code must not exceed 50 characters")
    private String labCode;
    
    @NotBlank(message = "Lab name is required")
    @Size(max = 200, message = "Lab name must not exceed 200 characters")
    private String name;
    
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;
    
    @NotBlank(message = "Location is required")
    @Size(max = 200, message = "Location must not exceed 200 characters")
    private String location;
    
    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1")
    @Max(value = 1000, message = "Capacity must not exceed 1000")
    private Integer capacity;
    
    @NotBlank(message = "Status is required")
    @Pattern(regexp = "AVAILABLE|MAINTENANCE|UNAVAILABLE", message = "Status must be AVAILABLE, MAINTENANCE, or UNAVAILABLE")
    private String status;
    
    @Size(max = 1000, message = "Facilities description must not exceed 1000 characters")
    private String facilities;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;
    
    // Constructors
    public LabDto() {}
    
    public LabDto(Long id, String labCode, String name, String location, Integer capacity, String status) {
        this.id = id;
        this.labCode = labCode;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.status = status;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getLabCode() {
        return labCode;
    }
    
    public void setLabCode(String labCode) {
        this.labCode = labCode;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public Integer getCapacity() {
        return capacity;
    }
    
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getFacilities() {
        return facilities;
    }
    
    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static LabDto fromEntity(Lab lab) {
        LabDto dto = new LabDto();
        dto.setId(lab.getId());
        dto.setLabCode(lab.getLabCode());
        dto.setName(lab.getName());
        dto.setDescription(lab.getDescription());
        dto.setLocation(lab.getLocation());
        dto.setCapacity(lab.getCapacity());
        dto.setStatus(lab.getStatus());
        dto.setFacilities(lab.getFacilities());
        return dto;
    }

    public static Lab toEntity(LabDto dto) {
        Lab lab = new Lab();
        lab.setId(dto.getId());
        lab.setLabCode(dto.getLabCode());
        lab.setName(dto.getName());
        lab.setDescription(dto.getDescription());
        lab.setLocation(dto.getLocation());
        lab.setCapacity(dto.getCapacity());
        lab.setStatus(dto.getStatus());
        lab.setFacilities(dto.getFacilities());
        return lab;
    }

}
