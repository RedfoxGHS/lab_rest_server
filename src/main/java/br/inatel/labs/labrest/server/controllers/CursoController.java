package br.inatel.labs.labrest.server.controllers;


import br.inatel.labs.labrest.server.entities.Curso;
import br.inatel.labs.labrest.server.services.CursoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController()
@RequestMapping("/curso")
public class CursoController {

    @Autowired
    private CursoService cursoService;


    @Operation(summary = "Lista todos os cursos cadastrados", tags = "GETs",
            description = "Endpoint para listar todos os cursos salvos em memoria. (Clique em [Try it out])")
    @GetMapping
    public List<Curso> listar() {
        List<Curso> cursos = cursoService.buscarTodosOsCursos();
        return cursos;
    }


    @Operation(summary = "Busca cursos por ID", tags = "GETs",
            description = "Endpoint para buscar um curso especifico pelo ID. (Clique em [Try it out])")
    @GetMapping("/{id}")
    public Curso buscar(
            @Parameter(description = "ID do Curso", example = "10")
            @PathVariable(value = "id") Long cursoId) {

        return cursoService.buscarCursoPeloId(cursoId);
    }


    @Operation(summary = "Cria novo curso", tags = "POSTs",
            description = "Endpoint para criar um novo curso. (Clique em [Try it out])")
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Curso criar(@RequestBody Curso curso) {
        return cursoService.criarNovoCurso(curso);
    }


    @Operation(summary = "Editar Curso", tags = "PUTs",
            description = "Endpoint para editar um curso existente. (Clique em [Try it out])\n\n" +
                    "Curso deve ter um Id valido")
    @PutMapping
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public Curso atualizar(@RequestBody Curso curso) {
        return cursoService.atualizarCurso(curso);
    }


    @Operation(summary = "Deletar curso", tags = "DELETEs",
            description = "Endpoint para deletar um curso existente. (Clique em [Try it out])")
    @DeleteMapping("/{id}")
    public void deletarPorId(@PathVariable("id") Long id) {
        boolean deleted = cursoService.deletaPorId(id);

        if (!deleted)
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Curso com Id " + id + " já não existe na base.");

    }

}
