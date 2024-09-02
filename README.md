
# [POC] State Machine

Java 11

https://docs.spring.io/spring-boot/docs/2.4.1/reference/htmlsingle/#getting-started-first-application



## Description

This application implements a StateMachine to manage the lifecycle of a Document. The state machine handles various states and events as follows:
- I: initialstate
- Pending: Document process has started
- e1: Document of type A created
- e2: Document of type B document created
- e3: Second event for type A document
- e4: Last event for type A document
- e5: Last event for type B document
- F: Final state

<img title="State machine" alt="State machine" src="/assets/images/state-diagram.png">

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
- POST /document/A: Creates a document of type A.
- POST /document/B: Creates a document of type B.
- DELETE /document/{id}: Delete document for specified ID.
- POST /event/: Sends an event to specific document.
```json
{
    "id": 1,
    "event": "E4"
}
```

