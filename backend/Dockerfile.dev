FROM gradle:8.5-jdk17 AS test-runner
WORKDIR /workspace
COPY . .
CMD ["gradle", "clean", "test"]