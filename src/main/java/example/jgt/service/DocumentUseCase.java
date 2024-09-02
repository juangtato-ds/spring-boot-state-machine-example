package example.jgt.service;

import example.jgt.model.domain.document.TextMineDocument;
import example.jgt.model.domain.document.TextMineDocumentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentUseCase {

    @Autowired
    private DocumentStateMachineManager manager;

    @Autowired
    private DocumentService documentService;

    public TextMineDocument createDocument(TextMineDocumentType type) {
        var result = this.documentService.createDocument(type);
        manager.init(result);
        return result;
    }

}
