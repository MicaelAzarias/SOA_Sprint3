package br.com.habitoplus.controller;

import br.com.habitoplus.dto.UsuarioRequest;
import br.com.habitoplus.dto.UsuarioResponse;
import br.com.habitoplus.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para gerenciamento de colaboradores.
 * Expõe endpoints CRUD para a entidade Usuario.
 */
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    /**
     * POST /api/v1/usuarios
     * Cadastra um novo colaborador no sistema.
     */
    @PostMapping
    public ResponseEntity<UsuarioResponse> cadastrar(@RequestBody @Valid UsuarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrar(request));
    }

    /**
     * GET /api/v1/usuarios
     * Retorna todos os colaboradores cadastrados.
     */
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    /**
     * GET /api/v1/usuarios/{colaboradorId}
     * Retorna os dados de um colaborador pelo seu ID.
     */
    @GetMapping("/{colaboradorId}")
    public ResponseEntity<UsuarioResponse> buscarPorColaboradorId(@PathVariable String colaboradorId) {
        return ResponseEntity.ok(service.buscarPorColaboradorId(colaboradorId));
    }

    /**
     * DELETE /api/v1/usuarios/{colaboradorId}
     * Remove um colaborador e todos os seus hábitos associados.
     */
    @DeleteMapping("/{colaboradorId}")
    public ResponseEntity<Void> deletar(@PathVariable String colaboradorId) {
        service.deletar(colaboradorId);
        return ResponseEntity.noContent().build();
    }
}
