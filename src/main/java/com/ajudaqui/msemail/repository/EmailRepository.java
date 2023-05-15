package com.ajudaqui.msemail.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ajudaqui.msemail.entity.Email;

public interface EmailRepository extends JpaRepository<Email, Long>{
	
	@Query(value="SELECT * FROM email WHERE user_id= :userId", nativeQuery = true)
	List<Email> emailByUser(Long userId);

}
