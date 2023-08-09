package com.betrybe.agrix.controllers;

import com.betrybe.agrix.controllers.dto.FarmDTO;
import com.betrybe.agrix.controllers.dto.ResponseDTO;
import com.betrybe.agrix.models.entities.Farm;
import com.betrybe.agrix.service.FarmService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("farms")
public class FarmController {
  
  private FarmService farmService;
  
  @Autowired
  public FarmController(FarmService farmService) {
    this.farmService = farmService;
  }

  @PostMapping()
  public ResponseEntity<ResponseDTO<Farm>> createFarm(@RequestBody FarmDTO farmDTO) {
    Farm newFarm = farmService.insertFarm(farmDTO.toFarm());
    ResponseDTO<Farm> responseDTO = new ResponseDTO<>("Fazenda criada com sucesso!", newFarm);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
  }

  @PutMapping("/{farmId}")
  public ResponseEntity<ResponseDTO<Farm>> updateFarm(@PathVariable Long farmId, @RequestBody FarmDTO farmDTO) {
    Optional<Farm> optionalFarm = farmService.updateFarm(farmId, farmDTO.toFarm());

    if (optionalFarm.isEmpty()) {
      ResponseDTO<Farm> responseDTO = new ResponseDTO<>(
          String.format("Não foi encontrado a fazenda de ID %d", farmId), null);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    ResponseDTO<Farm> responseDTO = new ResponseDTO<>(
        "Fazenda atualizada com sucesso!", optionalFarm.get());
    return ResponseEntity.ok(responseDTO);
  }

  @DeleteMapping("/{farmId}")
  public ResponseEntity<ResponseDTO<Farm>> removeFarmById(@PathVariable Long farmId) {
    Optional<Farm> optionalFarm = farmService.removeFarmById(farmId);

    if (optionalFarm.isEmpty()) {
      ResponseDTO<Farm> responseDTO = new ResponseDTO<>(
          String.format("Não foi encontrado a fazenda de ID %d", farmId), null);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    ResponseDTO<Farm> responseDTO = new ResponseDTO<>("Fazenda removida com sucesso!", null);
    return ResponseEntity.ok(responseDTO);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ResponseDTO<Farm>> getFarmById(@PathVariable Long farmId) {
    Optional<Farm> optionalFarm = farmService.getFarmById(farmId);

    if (optionalFarm.isEmpty()) {
      ResponseDTO<Farm> responseDTO = new ResponseDTO<>(
          String.format("Não foi encontrada a fazenda de ID %d", farmId), null);
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
    }

    ResponseDTO<Farm> responseDTO = new ResponseDTO<>("Fazenda encontrado com sucesso!", optionalFarm.get());
    return ResponseEntity.ok(responseDTO);
  }

  @GetMapping()
  public List<FarmDTO> getAllFarms() {
    List<Farm> allFarms = farmService.getAllFarms();
    return allFarms.stream()
        .map((farm) -> new FarmDTO(farm.getId(), farm.getName(), farm.getSize()))
        .collect(Collectors.toList());
  }

}
