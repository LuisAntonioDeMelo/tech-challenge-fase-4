#!/bin/bash
# Script to run SonarQube analysis for all microservices

# Gateway microservice
echo "Running analysis for gateway-ms..."
cd gateway-ms
mvn clean verify sonar:sonar -Dsonar.projectKey=gateway-ms -Dsonar.host.url=http://localhost:9000 -Dsonar.login=admin -Dsonar.password=admin
cd ..

# Cliente microservice
echo "Running analysis for cliente-ms..."
cd cliente-ms
mvn clean verify sonar:sonar -Dsonar.projectKey=cliente-ms -Dsonar.host.url=http://localhost:9000 -Dsonar.login=admin -Dsonar.password=admin
cd ..

# Produto microservice
echo "Running analysis for produto-ms..."
cd produto-ms
mvn clean verify sonar:sonar -Dsonar.projectKey=produto-ms -Dsonar.host.url=http://localhost:9000 -Dsonar.login=admin -Dsonar.password=admin
cd ..

# Pedido microservice
echo "Running analysis for pedido-ms..."
cd pedido-ms
mvn clean verify sonar:sonar -Dsonar.projectKey=pedido-ms -Dsonar.host.url=http://localhost:9000 -Dsonar.login=admin -Dsonar.password=admin
cd ..

# Pagamento microservice
echo "Running analysis for pagamento-ms..."
cd pagamento-ms
mvn clean verify sonar:sonar -Dsonar.projectKey=pagamento-ms -Dsonar.host.url=http://localhost:9000 -Dsonar.login=admin -Dsonar.password=admin
cd ..

echo "SonarQube analysis completed"
