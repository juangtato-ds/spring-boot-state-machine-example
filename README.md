
# [POC] State Machine

Java 11

https://docs.spring.io/spring-boot/docs/2.4.1/reference/htmlsingle/#getting-started-first-application



## Description

This application implements a StateMachine to manage the lifecycle of a Document. The state machine handles various states and events as follows:
- INITIAL: document is readay to start
- PENDING: first interaction was requested
- LEGACY_FLOW: old logic for documents
- EXTRACTING_TEXT: sending document to extract text
- TEXT_EXTRACTED: text has been received, in the state the custom-tags must be evaluated
- ANSWERING_CUSTOM_TAGS: custom tags request is sent
- FINAL: nothing more to do there

https://mermaid.live/

```
flowchart TD
    INITIAL((INITIAL)) -->|START| PENDING((PENDING))
    PENDING -->|START_LEGACY| LEGACY_FLOW((LEGACY_FLOW))
    LEGACY_FLOW -->|END_FLOW| FINAL((FINAL))
    PENDING -->|EXTRACT_TEXT| EXTRACTING_TEXT((EXTRACTING_TEXT))
    EXTRACTING_TEXT -->|TEXT_RECEIVED| TEXT_EXTRACTED((TEXT_EXTRACTED))
    TEXT_EXTRACTED -->|SEND_CUSTOM_TAGS| ANSWERING_CUSTOM_TAGS((ANSWERING_CUSTOM_TAGS))
    TEXT_EXTRACTED -->|END_FLOW| FINAL
    ANSWERING_CUSTOM_TAGS -->|END_FLOW| FINAL

```

<img title="State machine" alt="State machine" src="/assets/images/state-machine.png">

## Run

To run the application use the following command.

```bash
./mvnw spring-boot:run
```

## Usage

Once the application is running, it will be accessible via API on port 8080. The available endpoints are:

- GET /document/: Generates a list of documents with all possible combinations.
- GET /document/{id}: Returns info for document with the specified ID.
- GET /document/{id}/statemachine: Checks if the state machine exists for the document with the specified ID.
- POST /document/legacy: Creates a document for the legacy flow.
- POST /document/no-default: Creates a document for the new flow.
- DELETE /document/{id}: Delete document for specified ID.
- POST /event/: Sends an event to specific document.
    ```json
    {
        "id": 1,
        "event": "E4"
    }
    ```
- GET /event/{id}: Retrieve the in-memory state machine for that document.

