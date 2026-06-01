package com.librarymanagement.application.repository;

import com.librarymanagement.application.entity.Member;
import com.librarymanagement.application.entity.TransferBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransferBookRepository extends JpaRepository<TransferBook, Long> {

    Optional<TransferBook> findByMember(Member member);
}
