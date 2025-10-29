package com.unilab.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "labs")
public class Lab {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long labId;
    
    @Column(nullable = false, unique = true)
    private String labCode;
    
    @Column(nullable = false)
    private String name;
    
    @Column(length = 1000)
    private String description;
    
    @Column(nullable = false)
    private String location;
    
    @Column(nullable = false)
    private Integer capacity;
    
    @Column(nullable = false)
    private String status; // AVAILABLE, MAINTENANCE, OCCUPIED
    
    @Column(length = 1000)
    private String facilities; // JSON array or comma-separated list
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "lab", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings = new ArrayList<>();
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Constructors
    public Lab() {}
    
    public Lab(String labCode, String name, String location, Integer capacity, String status) {
        this.labCode = labCode;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.status = status;
    }
    
    // Getters and Setters
    public Long getId() {
        return labId;
    }
    
    public void setId(Long labId) {
        this.labId = labId;
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
    
    public List<Booking> getBookings() {
        return bookings;
    }
    
    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
