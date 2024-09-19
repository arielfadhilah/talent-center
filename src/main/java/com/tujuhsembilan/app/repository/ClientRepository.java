package com.tujuhsembilan.app.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tujuhsembilan.app.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {

}
