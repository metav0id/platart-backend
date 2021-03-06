package com.inventoryapp.demo.repositories;

import com.inventoryapp.demo.entities.Comerce;
import com.inventoryapp.demo.entities.MapMarker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MapMarkerRapository extends JpaRepository<MapMarker, Long> {
    MapMarker findByLng (String lng);
    MapMarker findByName (String name);
    @Query(value = "SELECT entry FROM MapMarker entry WHERE entry.lat = null ")
    List<MapMarker> findByCategory();



}
