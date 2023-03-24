package com.kmgeronimo.dentalclinicbackend.repository;

import com.kmgeronimo.dentalclinicbackend.entity.DentistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DentistRepository extends JpaRepository<DentistEntity, String > {
}
