FROM gradle:8.5-jdk17 AS ztest-runner
WORKDIR /workspace
COPY . .
CMD ["gradle", "clean", "test"]
