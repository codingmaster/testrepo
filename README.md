## Publications Watermarking Service

This is a simple REST API test application for publication watermarking service. 

### API

The default port is **8070**


| Request  |  Description  |
|---|---|
|`GET /api/v1/books`|  Returns a list of all available books |
|`GET /api/v1/books/{bookId}`| Returns a particular book with `{bookId}` |
|`GET /api/v1/journals`| Returns a list of all available journals  |
|`GET /api/v1/journals/{journalId}`| Returns a particular book with `{journalId}`  |
|`GET /api/v1/watermarks/{documentId}`| Returns a watermark for document `{documentId}` if exists  |
|`POST /api/v1/watermarks/{documentId}`| Initiates the asynchronous watermarking process for `{documentId}` and returns a status ticket.  |
|`GET /api/v1/tickets/{documentId}`| Get details of the status ticket for `{documentId}`  |

* In order to see which publications are available execute following requests:
`GET /api/v1/books`
`GET /api/v1/journals`

* After that you can watermark any publication with:

`POST /api/v1/watermarks/{documentId}`, where `{documentId}` is the id of publication from the previous request. With this request the watermarking process is initiated. 
The watermarking process is executed asynchronously. 
You can check the status of the watermarking with the returned ticket.

* This request returns a ticket with the status of watermarking

* You can check the status of the watermarking with:

`GET /api/v1/tickets/{ticketId}`, where `{ticketId}` is the id of the ticket from the previous request

* When the watermarking is finished you can get the watermark of the document with:

`GET /api/v1/watermarks/{documentId}`