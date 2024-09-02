package example.jgt.rest.controller;

import example.jgt.rest.model.EventRequest;
import example.jgt.service.EventService;
import example.jgt.service.exception.DocumentException;
import example.jgt.service.exception.EventException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/event")
public class EventRestController {
    @Autowired
    private EventService eventService;

    @PostMapping()
    @ResponseStatus(code = HttpStatus.OK)
    public void create(@RequestBody EventRequest request) {
        this.eventService.processEvent(request.getId(), request.getEvent());
    }

    @GetMapping("{id}")
    public String get(@PathVariable String id) {
        return this.eventService.getState(id);
    }

    @ExceptionHandler({ EventException.class })
    public ResponseEntity<Map<String, String>> eventExeptionHandler(Exception e) {
        var data = Map.ofEntries(Map.entry("error", e.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(data);
    }

    @ExceptionHandler({ DocumentException.class })
    public ResponseEntity<Map<String, String>> documentExeptionHandler(Exception e) {
        var data = Map.ofEntries(Map.entry("error", e.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(data);
    }
}
