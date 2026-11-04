package br.com.szella.orderapi;

import br.com.szella.orderapi.infrastructure.adapters.out.database.OrderJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public class OrderApiIntegrationTest {

    // 1. Configura um container efêmero do PostgreSQL
    @Container
    @ServiceConnection // Configura automaticamente a URL, User e Password no Spring Boot
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    // 2. Configura um container efêmero do Kafka
    @Container
    @ServiceConnection // Configura automaticamente o bootstrap-servers no Spring Boot
    static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("apache/kafka:3.8.0"));

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderJpaRepository orderJpaRepository;

    @BeforeEach
    void setUp() {
        // Limpa o banco de dados antes de cada teste
        orderJpaRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve criar uma ordem no banco de dados e retornar 201 Created")
    void shouldCreateOrderAndReturn201() throws Exception {

        String requestBody = """
                {
                    "customerId": "a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11",
                    "ticker": "ITUB4",
                    "quantity": 200,
                    "price": 32.10,
                    "type": "BUY"
                }
                """;

        // Executa a requisição HTTP simulada (sem precisar de rede real)
        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.status").value("PENDING"));

        // Valida se o registro realmente foi persistido no PostgreSQL via container
        assertEquals(1, orderJpaRepository.count());
    }
}
