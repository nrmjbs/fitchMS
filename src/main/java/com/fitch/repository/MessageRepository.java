package com.fitch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fitch.domain.Message;
import com.fitch.enums.MessageType;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

	// @Query(nativeQuery = true, value = "select id, message_text,
	// message_type, message_priority, message_create_ts from message where
	// message_type = ?1")
	@Query("select m from Message m where m.messageType = ?1")
	List<Message> findMessagesByMessageType(@Param(value = "messageType") MessageType messageType);

}
