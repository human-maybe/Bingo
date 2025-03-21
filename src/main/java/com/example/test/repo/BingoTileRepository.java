package com.example.test.repo;

import com.example.test.model.BingoTile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BingoTileRepository extends JpaRepository<BingoTile, Long> {
}