# 🤝 Contribuindo para o Tech Challenge Fase 4

Obrigado por considerar contribuir para nosso projeto! Este documento fornece diretrizes para garantir um processo de contribuição suave e eficiente.

## 📋 Índice

- [Código de Conduta](#-código-de-conduta)
- [Como Contribuir](#-como-contribuir)
- [Configuração do Ambiente](#️-configuração-do-ambiente)
- [Padrões de Código](#-padrões-de-código)
- [Processo de Pull Request](#-processo-de-pull-request)
- [Testes](#-testes)
- [Documentação](#-documentação)

## 📜 Código de Conduta

Este projeto segue o [Contributor Covenant](https://www.contributor-covenant.org/). Ao participar, você concorda em seguir este código de conduta.

## 🛠️ Como Contribuir

### Reportando Bugs 🐛

1. **Verifique se já existe** um issue similar
2. **Use o template** de bug report
3. **Forneça detalhes**: OS, versão Java, logs de erro
4. **Passos para reproduzir** o problema

### Sugerindo Melhorias 💡

1. **Procure por** sugestões similares existentes
2. **Use o template** de feature request
3. **Explique o caso de uso** e benefícios
4. **Considere alternativas** e possíveis impactos

### Contribuições de Código 👨‍💻

1. **Fork** o repositório
2. **Crie uma branch** para sua feature (`git checkout -b feature/amazing-feature`)
3. **Faça commit** das mudanças (`git commit -m 'Add: amazing feature'`)
4. **Push** para a branch (`git push origin feature/amazing-feature`)
5. **Abra um Pull Request**

## 🏗️ Configuração do Ambiente

### Pré-requisitos

```bash
# Java 17+
java --version

# Maven 3.8+
mvn --version

# Docker & Docker Compose
docker --version
docker-compose --version
```

### Setup Inicial

```bash
# Clone do repositório
git clone https://github.com/user/tech-challenge-fase-4.git
cd tech-challenge-fase-4

# Execute o script de setup
chmod +x setup.sh
./setup.sh
```

### IDE Configuration

#### IntelliJ IDEA

```bash
# Importar configurações de estilo
cp .idea/codeStyles/Project.xml ~/.idea/codeStyles/

# Instalar plugins recomendados:
# - Lombok
# - SonarLint
# - CheckStyle
```

#### VS Code

```bash
# Instalar extensões recomendadas
code --install-extension vscjava.vscode-java-pack
code --install-extension gabrielbb.vscode-lombok
code --install-extension sonarsource.sonarlint-vscode
```

## 📏 Padrões de Código

### Java Style Guide

Seguimos o [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html) com algumas adaptações:

#### Formatação
- **Indentação**: 4 espaços (não tabs)
- **Linha máxima**: 120 caracteres
- **Imports**: organizados e sem wildcards desnecessários

#### Naming Conventions
- **Classes**: PascalCase (`ClienteService`)
- **Métodos**: camelCase (`criarCliente()`)
- **Constantes**: UPPER_SNAKE_CASE (`MAX_RETRY_ATTEMPTS`)
- **Variáveis**: camelCase (`nomeCliente`)

#### Documentação
```java
/**
 * Cria um novo cliente no sistema.
 * 
 * @param cliente dados do cliente a ser criado
 * @return cliente criado com ID atribuído
 * @throws ClienteExistenteException se CPF já estiver cadastrado
 * @throws InvalidCpfException se CPF for inválido
 */
public Cliente criarCliente(Cliente cliente) {
    // implementação
}
```

### Conventional Commits

Usamos [Conventional Commits](https://www.conventionalcommits.org/):

```
<type>[optional scope]: <description>

[optional body]

[optional footer(s)]
```

#### Tipos Permitidos
- `feat`: nova funcionalidade
- `fix`: correção de bug
- `docs`: mudanças na documentação
- `style`: formatação, ponto e vírgula faltando, etc.
- `refactor`: refatoração de código
- `test`: adição ou modificação de testes
- `chore`: mudanças no build, CI, etc.

#### Exemplos
```bash
feat(cliente): adicionar validação de CPF

fix(pagamento): corrigir timeout na API do Mercado Pago

docs: atualizar README com instruções Docker

test(produto): adicionar testes para busca por categoria
```

## 🔄 Processo de Pull Request

### Checklist do PR

- [ ] **Código testado** localmente
- [ ] **Testes unitários** adicionados/atualizados
- [ ] **Documentação** atualizada se necessário
- [ ] **Commits** seguem Conventional Commits
- [ ] **Branch** atualizada com main/master
- [ ] **CI passa** sem erros

### Template do PR

```markdown
## 📋 Descrição
Breve descrição das mudanças realizadas.

## 🔄 Tipo de Mudança
- [ ] Bug fix (correção que resolve um problema)
- [ ] Nova feature (mudança que adiciona funcionalidade)
- [ ] Breaking change (mudança que quebra funcionalidade existente)
- [ ] Documentação (atualização na documentação)

## 🧪 Testes
Descreva os testes que você executou:
- [ ] Testes unitários passam
- [ ] Testes de integração passam
- [ ] Testado manualmente

## 📷 Screenshots (se aplicável)
Adicione screenshots para demonstrar as mudanças visuais.

## 📝 Checklist
- [ ] Código está formatado corretamente
- [ ] Documentação foi atualizada
- [ ] Commits seguem Conventional Commits
- [ ] CI está passando
```

### Review Guidelines

#### Para Reviewers
- **Seja construtivo** nos comentários
- **Foque na qualidade** do código, não no autor
- **Sugira melhorias** concretas
- **Aprovação rápida** para mudanças simples

#### Para Autores
- **Responda todos** os comentários
- **Faça mudanças** solicitadas ou explique por que não
- **Mantenha PRs** pequenos e focados
- **Teste localmente** antes de solicitar review

## 🧪 Testes

### Estrutura de Testes

```
src/test/java/
├── unit/           # Testes unitários
├── integration/    # Testes de integração
└── e2e/           # Testes end-to-end
```

### Padrões de Teste

#### Naming Convention
```java
// Padrão: should[ExpectedBehavior]When[StateUnderTest]
@Test
void shouldCreateClienteWhenValidDataProvided() {
    // Arrange
    Cliente cliente = new Cliente("86599902062", "João");
    
    // Act
    Cliente result = clienteService.criarCliente(cliente);
    
    // Assert
    assertNotNull(result.getId());
    assertEquals("João", result.getNome());
}
```

#### Test Coverage
- **Mínimo**: 80% de cobertura
- **Objetivo**: 90% de cobertura
- **Exclusões**: DTOs, configurações, classes de infraestrutura

### Executando Testes

```bash
# Todos os testes
mvn test

# Testes de um módulo específico
mvn test -pl cliente-ms

# Testes com cobertura
mvn test jacoco:report

# Testes de integração
mvn test -Dtest=**/*IntegrationTest

# Testes end-to-end
docker-compose -f docker-compose.test.yml up --abort-on-container-exit
```

## 📚 Documentação

### API Documentation

- **Swagger/OpenAPI** para endpoints
- **Comentários Javadoc** para métodos públicos
- **README** atualizado para mudanças significativas

### Arquitetura

- **Diagramas** atualizados quando necessário
- **Decisões técnicas** documentadas em ADRs
- **Configurações** explicadas no README

## 🚀 Release Process

### Versionamento

Seguimos [Semantic Versioning](https://semver.org/):

- `MAJOR`: mudanças incompatíveis na API
- `MINOR`: funcionalidades adicionadas de forma compatível
- `PATCH`: correções compatíveis com versões anteriores

### Workflow de Release

1. **Merge** na branch main
2. **CI/CD** executa testes e build
3. **Qualidade** verificada pelo SonarQube
4. **Deploy** automático no ambiente de staging
5. **Validação** manual se necessário
6. **Tag** de release criada automaticamente

## 🎯 Quality Gates

### Automated Checks

- ✅ **Compilação** sem erros
- ✅ **Testes unitários** passando
- ✅ **Cobertura** >= 80%
- ✅ **SonarQube Quality Gate** = A
- ✅ **Vulnerabilidades** de segurança = 0

### Manual Review

- ✅ **Code review** por pelo menos 1 pessoa
- ✅ **Documentação** atualizada
- ✅ **Testes funcionais** quando aplicável

## 🆘 Obtendo Ajuda

### Canais de Comunicação

- 💬 **Slack**: `#tech-challenge-help`
- 📧 **Email**: `dev-team@techchallenge.com`
- 🐛 **Issues**: Para bugs e features
- 📖 **Wiki**: Para documentação detalhada

### FAQ

**Q: Como rodar apenas um microserviço?**
```bash
cd cliente-ms
mvn spring-boot:run
```

**Q: Como resetar o ambiente local?**
```bash
docker-compose down -v
docker system prune -f
./setup.sh
```

**Q: Como debuggar um teste específico?**
```bash
mvn test -Dtest=ClienteInteractorTest#shouldCreateClienteWhenValidData -Dmaven.surefire.debug
```

## 🏆 Reconhecimentos

Contribuidores que fazem diferença são reconhecidos através de:

- 🌟 **Menções** no README
- 🏅 **Badges** especiais no GitHub
- 🎉 **Shout-outs** nas reuniões da equipe

---

**Obrigado por contribuir! Juntos construímos um projeto melhor! 🚀**
