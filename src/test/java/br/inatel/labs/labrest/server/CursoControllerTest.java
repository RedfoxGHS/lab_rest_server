package br.inatel.labs.labrest.server;

import br.inatel.labs.labrest.server.entities.Curso;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CursoControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void deveListarCursos() {

        webTestClient.get()
                .uri("/curso")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .returnResult();
    }

    @Test
    void dadoCursoComIDValido_quandoGetCursoPeloId_entaoRespondeComCursoValido() {
        Long idValido = 1L;

        Curso cursoRespondido = webTestClient.get()
                .uri("/curso/" + idValido)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Curso.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(cursoRespondido);
        assertEquals(cursoRespondido.getId(), idValido);
        assertThat(cursoRespondido).isNotNull();
        assertThat(idValido).isEqualTo(cursoRespondido.getId());
    }

    @Test
    void dadoCursoComIDInvalido_quandoGetCursoPeloID_entaoRespondeComStatusNotFound() {

        Long cursoIdValido = 99L;
        webTestClient.get()
                .uri("/curso/" + cursoIdValido)
                .exchange()
                .expectStatus()
                .isNotFound();
    }


    @Test
    public void dadoNovoCurso_quandoPostCurso_entaoRespondeComStatusCreatedECursoValido() {
        Curso novoCurso = new Curso();
        novoCurso.setDescricao("Curso Teste");
        novoCurso.setCargaHoraria(10);

        Curso cursoRespondido = webTestClient.post()
                .uri("/curso")
                .bodyValue(novoCurso)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody(Curso.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(cursoRespondido);
        assertNotNull(cursoRespondido.getId());
        assertEquals(novoCurso.getDescricao(), cursoRespondido.getDescricao());
        assertEquals(novoCurso.getCargaHoraria(), cursoRespondido.getCargaHoraria());
    }

    @Test
    public void dadoUmCursoExistente_quandoPutCurso_entaoRespondeComStatusAcceptedECursoAtualizado() {
        Curso cursoExistente = new Curso();
        cursoExistente.setId(1L);
        cursoExistente.setDescricao("Nova Descrição Test");
        cursoExistente.setCargaHoraria(666);


        Curso cursoRespondido = webTestClient.put()
                .uri("/curso")
                .bodyValue(cursoExistente)
                .exchange()
                .expectStatus().isAccepted()
                .expectBody(Curso.class)
                .returnResult().getResponseBody();


        assertNotNull(cursoRespondido);
        assertNotNull(cursoRespondido.getId());
        assertEquals(1L, cursoRespondido.getId());
        assertEquals(cursoExistente, cursoRespondido);
        assertEquals(cursoExistente.getDescricao(), cursoRespondido.getDescricao());
        assertEquals(cursoExistente.getCargaHoraria(), cursoRespondido.getCargaHoraria());
    }

    @Test
    public void dadoUmIDDeUmCursoExistente_quandoDeletePorIdDoCurso_entaoRemoverCursoERetornarStatusOk() {
        Long idCursoASerRemovido = 2L;


        webTestClient.delete()
                .uri("/curso/" + idCursoASerRemovido)
                .exchange()
                .expectStatus().isOk();

    }

    @Test
    public void dadoUmIDDeUmCursoInexistente_quandoDeletePorIdDoCurso_entaoResponderComStatusNoContentELancarResponseStatusException() {
        Long idCursoASerRemovido = 66L;


        webTestClient.delete()
                .uri("/curso/" + idCursoASerRemovido)
                .exchange()
                .expectStatus().isNoContent().expectBody(ResponseStatusException.class);

    }
}
