package io.pismo.pismoapi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = PismoApiApplication.class)
public class PismoApiApplicationTest {

    @Test
    @DisplayName("Carrega aplicação e verifica se carregou o contexto")
    public void loadApplicationContext(){
        System.out.println("=====================================");
    }

}
