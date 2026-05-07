package br.com.habitoplus.controller;

import br.com.habitoplus.dto.HabitoRequest;
import br.com.habitoplus.dto.HabitoResponse;
import br.com.habitoplus.service.HabitoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST responsável pelos endpoints de registro e consulta de hábitos.
 * Utiliza ResponseEntity para padronizar todos os retornos da API.
 */
@RestController
@RequestMapping("/api/v1/habitos")
public class HabitoController {

    @Autowired
    private HabitoService service;

    /**
     * POST /api/v1/habitos
     * Registra um novo hábito saudável para um colaborador.
     */
    @PostMapping
    public ResponseEntity<HabitoResponse> registrar(@RequestBody @Valid HabitoRequest request) {
        HabitoResponse response = service.registrarHabito(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /api/v1/habitos
     * Retorna todos os hábitos registrados no sistema.
     */
    @GetMapping
    public ResponseEntity<List<HabitoResponse>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    /**
     * GET /api/v1/habitos/{id}
     * Retorna um hábito específico pelo seu ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<HabitoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    /**
     * GET /api/v1/habitos/colaborador/{colaboradorId}
     * Retorna todos os hábitos de um colaborador.
     */
    @GetMapping("/colaborador/{colaboradorId}")
    public ResponseEntity<List<HabitoResponse>> listarPorColaborador(@PathVariable String colaboradorId) {
        return ResponseEntity.ok(service.listarPorColaborador(colaboradorId));
    }

    /**
     * PUT /api/v1/habitos/{id}
     * Atualiza um hábito existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HabitoResponse> atualizar(@PathVariable Long id,
                                                     @RequestBody @Valid HabitoRequest request) {
        return ResponseEntity.ok(service.atualizarHabito(id, request));
    }

    /**
     * DELETE /api/v1/habitos/{id}
     * Remove um hábito pelo seu ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletarHabito(id);
        return ResponseEntity.noContent().build();
    }
}
