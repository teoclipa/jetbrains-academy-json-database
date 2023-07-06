# jetbrains-academy-json-database
Project Description:
-------------------
This project is a JSON-based database client-server application. It allows storing and retrieving JSON objects as values with support for nested objects and partial retrieval of JSON values. The application is built in Java and utilizes the GSON library for JSON parsing and serialization. It also incorporates the JCommander library for command-line argument parsing.

Key Features:
-------------
- Store any JSON objects as values in the database
- Retrieve specific parts of JSON values using a JSON array key
- Set separate values inside JSON objects or create nested objects
- Handle creation and deletion of objects based on the specified JSON array key
- Command-line interface with argument parsing using JCommander

Usage:
------
The client application provides a command-line interface to interact with the server. It supports various operations, including setting values, retrieving values, and deleting values based on the specified JSON array key. Additionally, you can load commands from a file for batch processing.

Technology Stack:
-----------------
- Java
- GSON (Google JSON Library)
- JCommander (Command-line Argument Parser)
- Socket Programming

Instructions:
-------------
1. Start the server application
2. Launch the client application with appropriate command-line arguments to interact with the server
3. Execute commands to set, retrieve, or delete JSON values
4. View the server responses and output

Contributing:
-------------
Contributions are welcome! If you find any issues or want to enhance the functionality of the project, feel free to submit a pull request.
