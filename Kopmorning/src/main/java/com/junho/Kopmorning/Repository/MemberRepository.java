package com.junho.Kopmorning.Repository;

import com.junho.Kopmorning.Domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long>{
    Optional<Member> findByEmail(String email); //email을 기준으로 회원 찾기
    boolean existByEmail(String email); // email 존재 여부
}
