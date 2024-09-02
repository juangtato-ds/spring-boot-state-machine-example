package example.jgt.rest.controller;

import example.jgt.model.domain.document.TextMineDocument;
import example.jgt.model.domain.document.TextMineDocumentType;
import example.jgt.rest.model.DocumentResponse;
import example.jgt.service.DocumentService;
import example.jgt.service.DocumentStateMachineManager;
import example.jgt.service.DocumentUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/document")
public class DocumentRestController {
    @Autowired
    private DocumentService documentService;

    @Autowired
    private DocumentUseCase documentUseCase;

    @Autowired
    private DocumentStateMachineManager manager;

    @PostMapping("legacy")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<DocumentResponse> createDocumentA() {
        return createDocument(TextMineDocumentType.LEGACY);
    }

    @PostMapping("no-default")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<DocumentResponse> createDocumentB() {
        return createDocument(TextMineDocumentType.NO_DEFAULT_FIELDS);
    }

    @GetMapping("")
    @ResponseStatus(code = HttpStatus.OK)
    public List<DocumentResponse> list() {
        return this.documentService.list().stream().map(this::toDocumentResponse).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public DocumentResponse get(@PathVariable() String id) {
        try {
            return this.toDocumentResponse(this.documentService.get(id));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
    }

    @DeleteMapping("{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void delete(@PathVariable() String id) {
        try {
            this.documentService.delete(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No Content");
        }
    }

    @GetMapping("{id}/statemachine")
    @ResponseStatus(code = HttpStatus.OK)
    public boolean getStateMachine(@PathVariable() String id) {
        return manager.hasStateMachine(id);
    }

    private ResponseEntity<DocumentResponse> createDocument(TextMineDocumentType type) {
        TextMineDocument doc = documentUseCase.createDocument(type);
        if (doc == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        DocumentResponse response = toDocumentResponse(doc);
        return ResponseEntity.ok(response);
    }

    private DocumentResponse toDocumentResponse(TextMineDocument document) {
        return new DocumentResponse(document.getId(), document.getType().toString(),
                document.getState().toString());
    }
}
