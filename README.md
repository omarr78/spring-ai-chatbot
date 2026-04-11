# Spring AI Chat Application

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

The endpoint returns a plain text string with the AI-generated reply (not JSON, as it's a simple chat response).

#### Response Example:

```text
There are many great cars to consider when buying a new vehicle. Here are some popular options across various price ranges and interests:

**Mid-range ( $20,000 - $30,000)**

1. Toyota Corolla: A reliable and fuel-efficient sedan with a comfortable ride.
2. Honda Civic: A fun-to-drive compact car with good gas mileage and features like heated seats.
3. Hyundai Elantra: A feature-packed sedan with a smooth ride and strong engine options.

**Budget-friendly ( $15,000 - $20,000)**

1. Chevrolet Cruze: A solid economy car with a comfortable ride and low maintenance costs.
2. Kia Rio: A budget-friendly subcompact car with plenty of standard features and good fuel economy.
3. Ford Focus: A fun-to-drive compact car with a range of engine options and a premium feel.

**Luxury ( $30,000 - $50,000)**

1. Audi A4: A comfortable and feature-rich sedan with a smooth ride and strong engine options.
2. BMW 3 Series: A luxurious and performance-oriented compact sedan or wagon.
3. Mercedes-Benz C-Class: A premium and well-equipped sedan or coupe.

**Electric/Hybrid ( $30,000 - $50,000)**

1. Tesla Model 3: An electric sedan with a range of up to 326 miles and advanced features like Autopilot.
2. Toyota Prius: A hybrid compact car with excellent fuel economy and low emissions.
3. Honda Clarity Hybrid: A mid-size sedan with a smooth ride and strong engine options.

**SUV/Crossover ( $25,000 - $40,000)**

1. Honda CR-V: A reliable and practical SUV with a spacious interior and good fuel economy.
2. Toyota RAV4: A popular and feature-packed compact SUV with a roomy interior.
3. Subaru Forester: A safe and capable compact SUV with all-wheel drive and plenty of standard features.

These are just a few examples, and there are many other great cars available in each category. It's essential to research and test drive different models to find the best fit for your needs and budget.

```

## API Endpoints

- `GET /api/ai/chat?message={your_message}`: Generates an AI response to the provided message.

