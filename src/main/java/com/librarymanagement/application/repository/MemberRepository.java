package com.librarymanagement.application.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.librarymanagement.application.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long>{

    Optional<Member> findByMemberId(long memberId);

}
