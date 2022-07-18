package com.back.backend.classes.repo;

import com.back.backend.classes.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByNameContainingIgnoreCase(String name);
}
