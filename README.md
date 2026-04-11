# AI with Spring

This is a simple Spring Boot application that integrates with AI using Ollama to provide a chat endpoint. It allows users to send messages and receive AI-generated responses via a REST API.

## Features

- RESTful API endpoint for chatting with an AI model.
- Configurable AI model via Spring AI and Ollama.
- Easy setup with local Ollama instance.

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Ollama installed and running locally

## Setup and Installation

### 1. Install Ollama

Ollama is required to run the AI model locally. Follow these steps to install it on your system:

```bash
curl -fsSL https://ollama.com/install.sh | sh
```

This command downloads and installs Ollama.

### 2. Start Ollama Service

After installation, start the Ollama service:

```bash
ollama serve
```

**Note:** If you encounter an error like "listen tcp 127.0.0.1:11434: bind: address already in use", it means Ollama is already running. You can check with:

```bash
sudo lsof -i :11434
```

### 3. Pull the Required Model

Pull the `llama3.2:1b` model used by the application:

```bash
ollama pull llama3.2:1b
```

This downloads the model files. You can verify the installed models with:

```bash
ollama list
```

### 4. Build and Run the Application

Clone or navigate to the project directory, then build and run the Spring Boot application:

```bash
mvn clean install
mvn spring-boot:run
```

The application will start on `http://localhost:8080` by default.

## Configuration

The application is configured via `src/main/resources/application.yml`:

```yaml
spring:
  application:
    name: ai-with-spring
  ai:
    ollama:
      base-url: http://localhost:11434
      chat:
        options:
          model: llama3.2:1b
```

- `base-url`: URL of the local Ollama instance.
- `model`: The AI model to use (must be pulled via Ollama).

## Usage

Once the application is running, you can interact with the AI via the `/api/ai/chat` endpoint.

### Example Request

Send a GET request to the chat endpoint with a message parameter:

```bash
curl "http://localhost:8080/api/ai/chat?message=Hello, suggest a car for buying"
```

Replace `Hello, suggest a car for buying` with your desired message.

### Response

The endpoint returns a response with the AI-generated reply.

## API Endpoints

- `GET /api/ai/chat?message={your_message}`: Generates an AI response to the provided message.

