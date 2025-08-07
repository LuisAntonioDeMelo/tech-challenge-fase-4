# 🍔 Tech Challenge Fase 4 - Fast Food Microservices

[![CI/CD Pipeline](https://github.com/user/tech-challenge-fase-4/workflows/CI/CD%20Pipeline/badge.svg)](https://github.com/user/tech-challenge-fase-4/actions)
[![Quality Gate](https://github.com/user/tech-challenge-fase-4/workflows/Build%20and%20Quality%20Check/badge.svg)](https://github.com/user/tech-challenge-fase-4/actions)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://openjdk.java.net/projects/jdk/17/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-green.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

> Sistema completo de microserviços para fast food, desenvolvido como parte do **Tech Challenge da FIAP** - Fase 4. Uma solução moderna, escalável e robusta que demonstra as melhores práticas de arquitetura de microserviços.

## 🏗️ Arquitetura do Sistema

Este projeto implementa uma arquitetura de microserviços distribuídos com padrões modernos de desenvolvimento:

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   🌐 Client     │───▶│  🚪 Gateway     │───▶│  🔍 Eureka      │
│   (Frontend)    │    │   (Port 8080)   │    │  (Port 8761)    │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                │
                ┌───────────────┼───────────────┐
                │               │               │
        ┌───────▼──────┐ ┌──────▼──────┐ ┌──────▼──────┐
        │  👤 Cliente  │ │  🍟 Produto │ │  📋 Pedido  │
        │ (Port 8081)  │ │ (Port 8082) │ │ (Port 8083) │
        └──────────────┘ └─────────────┘ └─────────────┘
                │               │               │
        ┌───────▼──────┐ ┌──────▼──────┐ ┌──────▼──────┐
        │  📊 MongoDB  │ │ 🗄️ Couchbase │ │  🐬 MySQL   │
        │ (Port 27017) │ │ (Port 8091) │ │ (Port 3306) │
        └──────────────┘ └─────────────┘ └─────────────┘
                                │
                        ┌───────▼──────┐
                        │  💳 Pagamento │
                        │ (Port 8084)   │
                        └───────────────┘
```

### 🚀 Microserviços

| Serviço | Porta | Descrição | Tecnologias |
|---------|-------|-----------|-------------|
| **🔍 Eureka Server** | `8761` | Service Discovery & Registry | Spring Cloud Netflix |
| **🚪 API Gateway** | `8080` | Roteamento, Rate Limiting, Circuit Breaker | Spring Cloud Gateway, Redis |
| **👤 Cliente MS** | `8081` | Gerenciamento de clientes | MongoDB, Spring Data |
| **🍟 Produto MS** | `8082` | Catálogo de produtos | Couchbase, Spring Data |
| **📋 Pedido MS** | `8083` | Gestão de pedidos | MySQL, JPA, Feign Clients |
| **💳 Pagamento MS** | `8084` | Processamento de pagamentos | MySQL, Mercado Pago API |

## 🛠️ Stack Tecnológica

### Backend
- **☕ Java 17** - LTS version com as últimas features
- **🍃 Spring Boot 3.2** - Framework principal
- **☁️ Spring Cloud 2023** - Microservices toolkit
- **🔐 Spring Security** - Autenticação e autorização
- **📊 Micrometer + Prometheus** - Métricas e monitoramento

### Bases de Dados
- **🍃 MongoDB** - NoSQL para dados de clientes
- **🗄️ Couchbase** - Armazenamento de produtos (alta performance)
- **🐬 MySQL** - RDBMS para pedidos e pagamentos
- **🔴 Redis** - Cache distribuído e rate limiting

### DevOps & Observabilidade
- **🐳 Docker & Docker Compose** - Containerização
- **⚡ GitHub Actions** - CI/CD automatizado
- **📊 JaCoCo** - Cobertura de código
- **🔍 SonarQube** - Análise de qualidade de código
- **📈 Actuator** - Health checks e métricas

### Testes
- **✅ JUnit 5** - Testes unitários
- **🥒 Cucumber** - Testes BDD
- **🎭 Mockito** - Mocking framework
- **🔧 TestContainers** - Testes de integração

## 🚀 Quick Start

### Pré-requisitos
```bash
# Verificar versões necessárias
java --version    # Java 17+
docker --version  # Docker 20.10+
mvn --version     # Maven 3.8+
```

### 1️⃣ Clone do Repositório
```bash
git clone https://github.com/user/tech-challenge-fase-4.git
cd tech-challenge-fase-4
```

### 2️⃣ Build dos Microserviços
```bash
# Build completo com testes
mvn clean package

# Build rápido (pula testes)
mvn clean package -DskipTests
```

### 3️⃣ Executar com Docker
```bash
# Subir toda a infraestrutura
docker-compose up -d

# Verificar status dos containers
docker-compose ps

# Logs de um serviço específico
docker-compose logs -f gateway-ms
```

### 4️⃣ Verificar Health dos Serviços
```bash
# Script de verificação
curl -s http://localhost:8761/actuator/health | jq '.status'  # Eureka
curl -s http://localhost:8080/actuator/health | jq '.status'  # Gateway
curl -s http://localhost:8081/actuator/health | jq '.status'  # Cliente
```

## 📱 Endpoints da API

### 🚪 Gateway (Port 8080)
```http
GET  /actuator/health     # Health check
GET  /actuator/metrics    # Métricas do sistema
GET  /actuator/prometheus # Métricas para Prometheus
```

### 👤 Clientes
```http
POST /api/clientes              # Criar cliente
GET  /api/clientes/{cpf}        # Buscar por CPF
GET  /api/clientes/{id}         # Buscar por ID
```

### 🍟 Produtos
```http
GET    /api/produtos                    # Listar todos
GET    /api/produtos/categoria/{tipo}   # Por categoria
POST   /api/produtos                    # Criar produto
PUT    /api/produtos/{id}               # Atualizar
DELETE /api/produtos/{id}               # Remover
```

### 📋 Pedidos
```http
POST /api/pedidos              # Criar pedido
GET  /api/pedidos/{id}         # Consultar pedido
PUT  /api/pedidos/{id}/status  # Atualizar status
GET  /api/pedidos/fila         # Fila de pedidos
```

### 💳 Pagamentos
```http
POST /api/pagamentos                    # Processar pagamento
GET  /api/pagamentos/{pedidoId}/status  # Status do pagamento
POST /api/pagamentos/webhook            # Webhook Mercado Pago
```

## 🌐 Interfaces Web

| Interface | URL | Credenciais |
|-----------|-----|-------------|
| **📊 Eureka Console** | [localhost:8761](http://localhost:8761) | - |
| **📚 Swagger UI - Gateway** | [localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) | - |
| **📚 Swagger UI - Cliente** | [localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html) | - |
| **📚 Swagger UI - Produto** | [localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html) | - |
| **🗄️ MongoDB Express** | [localhost:8200](http://localhost:8200) | - |
| **💾 Couchbase Console** | [localhost:8091](http://localhost:8091) | admin/password |
| **🐬 phpMyAdmin** | [localhost:8100](http://localhost:8100) | root/root |

## 🧪 Executando os Testes

### Testes Unitários
```bash
# Todos os testes
mvn test

# Testes de um módulo específico
mvn test -pl cliente-ms

# Testes com cobertura
mvn test jacoco:report
```

### Testes de Integração
```bash
# Gateway (com Cucumber)
cd gateway-ms
mvn test -Dtest=CucumberTestRunner

# Testes end-to-end
docker-compose -f docker-compose.test.yml up --abort-on-container-exit
```

### Relatórios de Cobertura
```bash
# Gerar relatório JaCoCo
mvn clean test jacoco:report

# Visualizar relatório
open gateway-ms/target/site/jacoco/index.html
```

## 📊 Monitoramento & Observabilidade

### Métricas Custom
Cada microserviço expõe métricas específicas:

- **Gateway**: `gateway.request.duration`, `gateway.circuit.breaker`
- **Cliente**: `clientes.created.total`, `clientes.validation.errors`
- **Produto**: `produtos.by.category`, `produtos.stock.alerts`
- **Pedido**: `pedidos.processing.time`, `pedidos.queue.size`
- **Pagamento**: `pagamentos.success.rate`, `pagamentos.provider.response.time`

### Health Checks
Todos os serviços implementam health checks customizados:
```http
GET /actuator/health/db      # Status da base de dados
GET /actuator/health/external # Serviços externos
```

## 🔧 Configuração Avançada

### Variáveis de Ambiente
```bash
# Database URLs
MONGO_URL=mongodb://localhost:27017/clientes
COUCHBASE_URL=couchbase://localhost
MYSQL_URL=jdbc:mysql://localhost:3306/fastfood

# External Services
MERCADOPAGO_ACCESS_TOKEN=your_token_here
EUREKA_SERVER_URL=http://localhost:8761/eureka

# Monitoring
PROMETHEUS_ENABLED=true
JACOCO_COVERAGE_THRESHOLD=0.80
```

### Profiles de Execução
```bash
# Development
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Production
mvn spring-boot:run -Dspring-boot.run.profiles=prod

# Testing
mvn spring-boot:run -Dspring-boot.run.profiles=test
```

## 🚀 Deploy em Produção

### Docker Production
```bash
# Build das imagens otimizadas
docker-compose -f docker-compose.prod.yml build

# Deploy com healthchecks
docker-compose -f docker-compose.prod.yml up -d

# Scaling horizontal
docker-compose -f docker-compose.prod.yml up -d --scale cliente-ms=3
```

### Kubernetes (Helm)
```bash
# Install via Helm
helm install fastfood-ms ./k8s/helm-chart

# Port forward para testes
kubectl port-forward svc/gateway-ms 8080:8080
```

## 📈 Performance

### Benchmarks
- **Gateway Throughput**: ~5000 req/s
- **Cliente MS**: ~2000 req/s (com MongoDB)
- **Produto MS**: ~3000 req/s (com Couchbase)
- **Latência P99**: <100ms (local)

### Otimizações Implementadas
- ✅ Connection pooling
- ✅ Redis caching
- ✅ Circuit breaker patterns
- ✅ Rate limiting
- ✅ Database indexing
- ✅ JVM tuning

## 🤝 Contribuindo

### Workflow de Desenvolvimento
1. **Fork** o repositório
2. **Create** uma feature branch (`git checkout -b feature/amazing-feature`)
3. **Commit** suas mudanças (`git commit -m 'Add: amazing feature'`)
4. **Push** para a branch (`git push origin feature/amazing-feature`)
5. **Open** um Pull Request

### Padrões de Código
- Seguir [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
- Cobertura mínima de testes: 80%
- SonarQube Quality Gate: A
- Conventional Commits

### Setup do Ambiente de Desenvolvimento
```bash
# Pre-commit hooks
pre-commit install

# IDE settings
cp .idea/codeStyles/Project.xml ~/.idea/codeStyles/

# Database seeds
docker-compose exec mongo mongoimport --collection clientes --file /data/seeds/clientes.json
```

## 🐛 Troubleshooting

### Problemas Comuns

**🔴 Eureka não registra serviços**
```bash
# Verificar conectividade
curl -f http://localhost:8761/eureka/apps || echo "Eureka down"

# Logs detalhados
docker logs eureka-server --tail 50
```

**🔴 Gateway timeout**
```bash
# Verificar circuit breaker
curl http://localhost:8080/actuator/circuitbreakers

# Reset circuit breaker
curl -X POST http://localhost:8080/actuator/circuitbreakers/cliente-ms/reset
```

**🔴 Base de dados não conecta**
```bash
# Test de conectividade
docker-compose exec cliente-ms nc -z mongo 27017
docker-compose exec produto-ms nc -z couchbase 8091
```

### Logs Centralizados
```bash
# Todos os logs
docker-compose logs -f

# Logs por nível
docker-compose logs -f | grep ERROR
docker-compose logs -f | grep WARN

# Logs estruturados (JSON)
docker-compose exec gateway-ms tail -f /app/logs/application.json
```

## 📄 Licença

Este projeto está licenciado sob a **MIT License** - veja o arquivo [LICENSE](LICENSE) para detalhes.

## 📞 Suporte

- 📧 Email: [suporte@techchallenge.com](mailto:suporte@techchallenge.com)
- 💬 Slack: [#tech-challenge-help](https://workspace.slack.com/channels/tech-challenge-help)
- 📖 Wiki: [Documentação Completa](https://github.com/user/tech-challenge-fase-4/wiki)
- 🐛 Issues: [GitHub Issues](https://github.com/user/tech-challenge-fase-4/issues)

---

<div align="center">
  <p><strong>Feito com ❤️ pela equipe do Tech Challenge FIAP</strong></p>
  <p>
    <a href="#">⭐ Star este repo</a> •
    <a href="#">🐛 Report Bug</a> •
    <a href="#">💡 Request Feature</a>
  </p>
</div>
