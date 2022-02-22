package de.leuphana.cosa.documentsystem.behaviour.service;

import de.leuphana.cosa.documentsystem.structure.Documentable;

public interface DocumentService {
    String DOCUMENT_CREATED_TOPIC = "documentservice/document/created";
    String DOCUMENT_KEY = "document";

    void createDocument(Documentable documentable);
}
