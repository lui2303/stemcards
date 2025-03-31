#Design Choices:
- System.nanoTime() for ID generation as it is very unlikely that two cards from the same user in the same path get created in the same second.
Pros: Easy ID management
Cons: multiple logins could cause problems, server/client lag could be issues but shouldn't be to bad for this use case
- 