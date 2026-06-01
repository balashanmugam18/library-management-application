package com.practice.librarymanagement.repository;

import com.practice.librarymanagement.entity.Member;
import com.practice.librarymanagement.entity.TransferBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransferBookRepository extends JpaRepository<TransferBook, Long> {

    Optional<TransferBook> findByMember(Member member);
}
