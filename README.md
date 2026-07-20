# Speed Violation Service
Microserviço desenvolvido como prova prática para a Velsis Sistemas e Tecnologia Viária.  
O serviço processa leituras de velocidade captadas por equipamentos de fiscalização, aplica
a tolerância legal, calcula o excesso percentual e determina se houve infração conforme o
Código de Trânsito Brasileiro (CTB). Infrações são armazenadas em memória e podem ser
consultadas por placa.

---

## 1. Tecnologias Utilizadas
- Java 21
- Spring Boot 3.x
- Maven
- Spring Web
- Spring Validation
- Lombok (opcional)
- JUnit 5
- Swagger/OpenAPI (diferencial)
- Jacoco (cobertura de testes)

---

## 2. Estrutura do Projeto

workspace_velsis/
├── pom.xml
├── README.md
└── src/
		├── main/
		│    ├── java/
		│    │    └── com/
		│    │         └── mulato/
		│    │              └── api/
		│    │                   └── speedviolation/
		│    └── resources/
		│         └── application.yml
		└── test/
			└── java/
				└── com/
					└── mulato/
							└── api/
								└── speedviolation/

---

## 3. Como Executar

### Pré-requisitos
- JDK 21 instalado
- Maven 3.9+ instalado

### Rodando o projeto

Passo 1: Limpar e compilar o projeto
mvn clean install

Passo 2: Executar a aplicação usando o Maven
mvn spring-boot:run

Passo 3: Executar a aplicação usando o arquivo JAR
java -jar target/speedviolation-1.0.0.jar

Passo 4: Rodar os testes
mvn test

A aplicação inicia na porta padrão 8080.
Para alterar a porta, edite o arquivo:
src/main/resources/application.properties

Exemplo:
server.port=8080

### Desafios Encontrados Durante o Desenvolvimento

Durante o desenvolvimento deste projeto, alguns desafios técnicos se destacaram e contribuíram para o amadurecimento da solução. O primeiro deles foi a configuração do ambiente de compilação. Embora o projeto utilizasse recursos modernos do Java, como records e switch expressions, o Maven inicialmente estava configurado para compilar com Java 8, o que gerou erros de incompatibilidade. A correção exigiu o ajuste explícito das propriedades de compilação para Java 21 e a adoção do spring-boot-starter-parent, garantindo que todas as dependências fossem resolvidas corretamente.

Outro ponto importante foi a estruturação do contexto do Spring Boot. No início, os testes não conseguiam localizar a classe principal da aplicação, resultando no erro “Unable to find a @SpringBootConfiguration”. A criação da classe SpeedViolationApplication e a organização adequada dos pacotes resolveram esse problema e permitiram que os testes de controller fossem executados corretamente.

O tratamento de erros também exigiu atenção especial. Alguns cenários, como header ausente ou valores inválidos para enums, estavam retornando respostas 500, quando o comportamento esperado era 400. A implementação de handlers específicos no GlobalExceptionHandler garantiu que o serviço respondesse de forma consistente e alinhada com as regras da prova.

Por fim, a implementação do endpoint de consulta trouxe desafios adicionais relacionados à estrutura de pacotes e imports ausentes. A criação do ViolationQueryService e a correção dos imports no controller eliminaram os erros de compilação e permitiram a inclusão de testes dedicados para esse endpoint.

Esses desafios, embora naturais em um projeto com múltiplas camadas, contribuíram para uma solução mais robusta, bem estruturada e alinhada com as boas práticas do ecossistema Spring Boot.