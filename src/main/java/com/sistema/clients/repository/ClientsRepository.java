package com.sistema.clients.repository;

import com.sistema.clients.model.entity.ClientsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientsRepository extends JpaRepository<ClientsEntity, Integer> {

    ClientsEntity findByIdAndCpf(Integer id, String Cpf);
}
