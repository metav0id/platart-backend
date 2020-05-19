package com.inventoryapp.demo.repositories;

import com.inventoryapp.demo.entities.Comerce;
import com.inventoryapp.demo.entities.MapMarker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MapMarkerRapository extends JpaRepository<MapMarker, Long> {
    MapMarker findByName (String name);
    @Query(value = "SELECT entry FROM MapMarker entry WHERE entry.lat = null ")
    List<MapMarker> findByCategory();



}
