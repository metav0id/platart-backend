package com.inventoryapp.demo.repositories;

import com.inventoryapp.demo.entities.MapMarker;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MapMarkerRapository extends JpaRepository<MapMarker, Long> {
    MapMarker findByName (String name);

}
