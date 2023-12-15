package com.emreilgar.repository;


import com.emreilgar.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails,Long> {

    UserDetails findUserDetailsById(Long userDetailsId);

}
