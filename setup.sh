#!/bin/bash

# Tech Challenge Fase 4 - Setup Script
# Este script automatiza a configuração inicial do projeto

set -e

# Cores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Função para imprimir mensagens coloridas
print_message() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

print_header() {
    echo -e "${BLUE}"
    echo "╔══════════════════════════════════════════════════════════════╗"
    echo "║                Tech Challenge Fase 4 - Setup                ║"
    echo "║           Sistema de Microserviços para Fast Food           ║"
    echo "╚══════════════════════════════════════════════════════════════╝"
    echo -e "${NC}"
}

# Função para verificar pré-requisitos
check_prerequisites() {
    print_message "Verificando pré-requisitos..."
    
    # Verificar Java
    if command -v java &> /dev/null; then
        JAVA_VERSION=$(java -version 2>&1 | grep -oP 'version ".*?"' | sed 's/version "//;s/"//')
        print_message "Java encontrado: $JAVA_VERSION"
        
        # Verificar se é Java 17 ou superior
        JAVA_MAJOR=$(echo $JAVA_VERSION | cut -d. -f1)
        if [ "$JAVA_MAJOR" -lt 17 ]; then
            print_error "Java 17 ou superior é necessário. Versão encontrada: $JAVA_VERSION"
            exit 1
        fi
    else
        print_error "Java não encontrado. Por favor, instale o Java 17 ou superior."
        exit 1
    fi
    
    # Verificar Maven
    if command -v mvn &> /dev/null; then
        MVN_VERSION=$(mvn -version | head -n 1)
        print_message "Maven encontrado: $MVN_VERSION"
    else
        print_error "Maven não encontrado. Por favor, instale o Apache Maven."
        exit 1
    fi
    
    # Verificar Docker
    if command -v docker &> /dev/null; then
        DOCKER_VERSION=$(docker --version)
        print_message "Docker encontrado: $DOCKER_VERSION"
    else
        print_error "Docker não encontrado. Por favor, instale o Docker."
        exit 1
    fi
    
    # Verificar Docker Compose
    if command -v docker-compose &> /dev/null; then
        COMPOSE_VERSION=$(docker-compose --version)
        print_message "Docker Compose encontrado: $COMPOSE_VERSION"
    else
        print_warning "Docker Compose não encontrado. Tentando usar 'docker compose'..."
        if docker compose version &> /dev/null; then
            print_message "Docker Compose (plugin) encontrado"
        else
            print_error "Docker Compose não encontrado. Por favor, instale o Docker Compose."
            exit 1
        fi
    fi
    
    print_message "✅ Todos os pré-requisitos verificados com sucesso!"
}

# Função para fazer o build dos projetos
build_projects() {
    print_message "Iniciando build dos projetos..."
    
    print_message "Limpando projetos anteriores..."
    mvn clean -q
    
    print_message "Compilando todos os microserviços..."
    mvn compile -T 4 -q
    
    print_message "Executando testes unitários..."
    mvn test -T 4 -q
    
    print_message "Gerando pacotes..."
    mvn package -DskipTests -T 4 -q
    
    print_message "✅ Build concluído com sucesso!"
}

# Função para iniciar os containers
start_containers() {
    print_message "Iniciando containers Docker..."
    
    print_message "Parando containers existentes..."
    docker-compose down --remove-orphans || true
    
    print_message "Iniciando infraestrutura (bases de dados, etc.)..."
    docker-compose up -d mongo mysql redis couchbase
    
    print_message "Aguardando bases de dados ficarem prontas..."
    sleep 30
    
    print_message "Iniciando Eureka Server..."
    docker-compose up -d eureka-server
    
    print_message "Aguardando Eureka Server..."
    sleep 15
    
    print_message "Iniciando microserviços..."
    docker-compose up -d gateway-ms cliente-ms produto-ms pedido-ms pagamento-ms
    
    print_message "✅ Todos os containers iniciados!"
}

# Função para verificar o status dos serviços
check_services() {
    print_message "Verificando status dos serviços..."
    
    services=(
        "eureka-server:8761"
        "gateway-ms:8080"
        "cliente-ms:8081"
        "produto-ms:8082"
        "pedido-ms:8083"
        "pagamento-ms:8084"
    )
    
    for service in "${services[@]}"; do
        name=$(echo $service | cut -d: -f1)
        port=$(echo $service | cut -d: -f2)
        
        print_message "Verificando $name (porta $port)..."
        
        # Tentar conectar na porta
        if timeout 5 bash -c "cat < /dev/null > /dev/tcp/localhost/$port"; then
            print_message "✅ $name está rodando na porta $port"
        else
            print_warning "⚠️ $name não está respondendo na porta $port"
        fi
    done
}

# Função para mostrar URLs úteis
show_urls() {
    echo -e "${BLUE}"
    echo "╔══════════════════════════════════════════════════════════════╗"
    echo "║                    URLs Importantes                         ║"
    echo "╠══════════════════════════════════════════════════════════════╣"
    echo "║ 🔍 Eureka Server:   http://localhost:8761                   ║"
    echo "║ 🚪 API Gateway:     http://localhost:8080                   ║"
    echo "║ 📚 Gateway Swagger: http://localhost:8080/swagger-ui.html   ║"
    echo "║ 👤 Cliente API:     http://localhost:8081                   ║"
    echo "║ 🍟 Produto API:     http://localhost:8082                   ║"
    echo "║ 📋 Pedido API:      http://localhost:8083                   ║"
    echo "║ 💳 Pagamento API:   http://localhost:8084                   ║"
    echo "║                                                              ║"
    echo "║ 📊 MongoDB Express: http://localhost:8200                   ║"
    echo "║ 💾 Couchbase:       http://localhost:8091                   ║"
    echo "║ 🐬 phpMyAdmin:      http://localhost:8100                   ║"
    echo "╚══════════════════════════════════════════════════════════════╝"
    echo -e "${NC}"
}

# Função para mostrar comandos úteis
show_commands() {
    echo -e "${BLUE}"
    echo "╔══════════════════════════════════════════════════════════════╗"
    echo "║                    Comandos Úteis                           ║"
    echo "╠══════════════════════════════════════════════════════════════╣"
    echo "║ Ver logs:           docker-compose logs -f [service]         ║"
    echo "║ Parar tudo:         docker-compose down                     ║"
    echo "║ Rebuild:            docker-compose build --no-cache         ║"
    echo "║ Status:             docker-compose ps                       ║"
    echo "║ Executar testes:    mvn test                                ║"
    echo "║ Gerar cobertura:    mvn test jacoco:report                  ║"
    echo "╚══════════════════════════════════════════════════════════════╝"
    echo -e "${NC}"
}

# Função principal
main() {
    print_header
    
    case "${1:-all}" in
        "prereq"|"prerequisites")
            check_prerequisites
            ;;
        "build")
            check_prerequisites
            build_projects
            ;;
        "start")
            start_containers
            ;;
        "status")
            check_services
            ;;
        "urls")
            show_urls
            ;;
        "help")
            echo "Uso: $0 [command]"
            echo ""
            echo "Comandos disponíveis:"
            echo "  all          - Executa setup completo (default)"
            echo "  prereq       - Verifica pré-requisitos"
            echo "  build        - Faz build dos projetos"
            echo "  start        - Inicia containers"
            echo "  status       - Verifica status dos serviços"
            echo "  urls         - Mostra URLs importantes"
            echo "  help         - Mostra esta ajuda"
            ;;
        "all"|*)
            check_prerequisites
            build_projects
            start_containers
            sleep 30
            check_services
            show_urls
            show_commands
            print_message "🎉 Setup completo! O sistema está rodando."
            ;;
    esac
}

# Executar função principal com argumentos
main "$@"
