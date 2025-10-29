package com.unilab.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

@Schema(description = "Represents a single lab booking slot with its own time and details")
public class LabBookingSlotDto {

    @NotNull(message = "Lab ID is required")
    @Schema(description = "ID of the lab to be booked", example = "6", required = true)
    private Long labId;

    @NotNull(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    @Schema(description = "Title or topic for this booking slot", example = "AI Fundamentals Workshop")
    private String title;

    @Schema(description = "Optional description about the session", example = "Introduction to neural networks using TensorFlow")
    private String description;

    @NotNull(message = "Participants count is required")
    @Positive(message = "Participants count must be greater than 0")
    @Schema(description = "Number of participants expected", example = "25")
    private Integer participantsCount;

    @NotNull(message = "Start time is required")
    @Schema(description = "Start time of the booking", example = "2025-10-27T08:00:00")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    @Schema(description = "End time of the booking", example = "2025-10-27T10:00:00")
    private LocalDateTime endTime;

    // Getters and Setters
    public Long getLabId() { return labId; }
    public void setLabId(Long labId) { this.labId = labId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getParticipantsCount() { return participantsCount; }
    public void setParticipantsCount(Integer participantsCount) { this.participantsCount = participantsCount; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }
}
