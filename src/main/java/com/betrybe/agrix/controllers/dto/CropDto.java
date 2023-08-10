package com.betrybe.agrix.controllers.dto;

import com.betrybe.agrix.models.entities.Crop;

public record CropDto(Long id, String name, Long farmId, Double plantedArea) {

  public Crop toCrop() {
    return new Crop(id, farmId, name, plantedArea);
  }

}