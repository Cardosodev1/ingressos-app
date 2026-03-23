# 🎟️ Ingressos App - Sistema de Vendas de Ingressos (Microsserviços)

Este é um projeto fullstack desenvolvido para simular um ambiente real e escalável de venda de ingressos para eventos. A arquitetura foi desenhada utilizando o padrão de Microsserviços, com comunicação síncrona (REST) e assíncrona (Mensageria), garantindo alta disponibilidade e resiliência.

## 🏗️ Arquitetura do Sistema

O sistema é composto pelos seguintes módulos principais:

* **Frontend (Angular):** Interface do usuário para navegação no catálogo e checkout.
* **API Gateway (Spring Cloud):** Porta de entrada única, responsável por roteamento e Rate Limiting.
* **IAM (Keycloak):** Gestão de Identidade e Acesso, protegendo as rotas e emitindo tokens JWT.
* **Catálogo Service (Spring Boot + MongoDB + Redis):** Microsserviço responsável pela vitrine de eventos. Utiliza MongoDB pela flexibilidade de estrutura dos eventos e Redis para cache de buscas.
* **Reserva Service (Spring Boot + MySQL):** O coração transacional. Gerencia os carrinhos, valida regras de meia-entrada e aplica *locks* temporários para evitar overbooking de assentos.
* **Workers Assíncronos (Spring Kafka):** Consumidores que escutam eventos de checkout para processar pagamentos de forma resiliente e gerar ingressos digitais.

## 🚀 Tecnologias Utilizadas

**Frontend:**
* Angular
* Stripe Elements (Integração de Pagamento)

**Backend:**
* Java 21
* Spring Boot 3
* Spring Cloud Gateway
* Spring Security & OAuth2 Resource Server

**Infraestrutura & Dados:**
* Docker & Docker Compose
* MySQL (Transações e ACID)
* MongoDB (Catálogo Flexível)
* Redis (Caching e Distributed Lock)
* Apache Kafka (Mensageria Assíncrona via KRaft)
* Keycloak (Autenticação e Autorização)

## 📁 Estrutura do Monorepo

```text
ingressos-app/
├── frontend/           # Aplicação SPA em Angular
├── backend/            # Ecossistema Spring Boot
│   ├── api-gateway/    # Roteamento e entrada
│   ├── catalogo-service/ # Serviço de eventos
│   ├── reserva-service/  # Serviço de checkout
│   └── workers/        # Consumidores Kafka (Pagamentos e Notificações)
└── infra/              # Configurações de containers e scripts
    └── docker-compose.yml
```

## 🛠️ Como executar o projeto localmente

**1. Pré-requisitos**
* Docker e Docker Compose instalados.
* Java 21 (JDK).
* Node.js (versão LTS).

**2. Subindo a Infraestrutura Base**

Antes de rodar as aplicações, é necessário iniciar os bancos de dados, o cache, o Kafka e o Keycloak.

```text
cd infra
docker-compose up -d
```

Aguarde alguns instantes para que todos os containers fiquem com status `healthy`.

**3. Configuração do Keycloak (Primeiro Uso)**
1. Acesse `http://localhost:8081` e faça login com `admin` / `admin`.
2. Crie um Realm chamado `ingressos-app`.
3. Configure os clients `angular-frontend` (Public) e `spring-gateway` (Confidential).
4. Crie as roles `ROLE_ADMIN` e `ROLE_CLIENTE` e adicione usuários de teste.

(Instruções detalhadas de execução dos microsserviços e do frontend serão adicionadas nas próximas etapas do desenvolvimento).

---

Desenvolvido por Guilherme - Projeto de estudo focado em engenharia de software, arquitetura de sistemas e melhores práticas de mercado.
