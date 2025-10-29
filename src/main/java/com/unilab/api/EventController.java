package com.unilab.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

// Response DTO - khớp với Flutter Event model
record EventResponse(
    Long id, 
    String title, 
    String description, 
    String startTime, 
    String endTime, 
    String status, 
    String location, 
    Integer capacity, 
    Integer bookedCount
) {}

@RestController
@RequestMapping("/api/events")
@Tag(name = "Events")
public class EventController {
    private final List<EventResponse> inMemoryEvents = new ArrayList<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    public EventController() {
        // Thêm mock data để test
        LocalDateTime now = LocalDateTime.now();
        inMemoryEvents.add(new EventResponse(
            1L,
            "Java Programming Workshop",
            "Learn advanced Java programming techniques and best practices",
            now.plusDays(1).format(formatter),
            now.plusDays(1).plusHours(3).format(formatter),
            "ACTIVE",
            "Computer Lab 1",
            30,
            15
        ));
        inMemoryEvents.add(new EventResponse(
            2L,
            "Database Design Seminar",
            "Introduction to database design and SQL optimization",
            now.plusDays(2).format(formatter),
            now.plusDays(2).plusHours(2).format(formatter),
            "ACTIVE",
            "Computer Lab 2",
            25,
            20
        ));
        inMemoryEvents.add(new EventResponse(
            3L,
            "Mobile Development Class",
            "Build mobile apps with Flutter and React Native",
            now.plusDays(3).format(formatter),
            now.plusDays(3).plusHours(4).format(formatter),
            "ACTIVE",
            "Multimedia Lab",
            35,
            35
        ));
        inMemoryEvents.add(new EventResponse(
            4L,
            "Network Security Lab",
            "Hands-on network security and penetration testing",
            now.plusDays(5).format(formatter),
            now.plusDays(5).plusHours(3).format(formatter),
            "PENDING",
            "Network Lab",
            20,
            8
        ));
    }

    @GetMapping
    @Operation(summary = "List events")
    public ResponseEntity<List<EventResponse>> list() {
        return ResponseEntity.ok(inMemoryEvents);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get event by ID")
    public ResponseEntity<EventResponse> getById(@PathVariable Long id) {
        return inMemoryEvents.stream()
            .filter(e -> e.id().equals(id))
            .findFirst()
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create an event")
    public ResponseEntity<EventResponse> create(@RequestBody EventResponse request) {
        EventResponse created = new EventResponse(
            (long) (inMemoryEvents.size() + 1),
            request.title(),
            request.description(),
            request.startTime(),
            request.endTime(),
            request.status() != null ? request.status() : "PENDING",
            request.location(),
            request.capacity(),
            request.bookedCount() != null ? request.bookedCount() : 0
        );
        inMemoryEvents.add(created);
        return ResponseEntity.ok(created);
    }
}


