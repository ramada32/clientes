package com.sistema.clients.repository;

import com.sistema.clients.model.entity.ClientsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientsRepository extends JpaRepository<ClientsEntity, Integer> {


    //tambem é possivel fazer script com query nativa EX:
//    @Transactional
//    @Modifying
//    @Query(value = "UPDATE Clients u set EMAIL_VERIFICATION_STATUS =:emailVerificationStatus where u.USER_ID = :userId",
//            nativeQuery = true)
//    void updateUser(@Param("emailVerificationStatus") boolean emailVerificationStatus, @Param("userId") String userId);
    ClientsEntity findByIdAndCpf(Integer id, String Cpf);
}
