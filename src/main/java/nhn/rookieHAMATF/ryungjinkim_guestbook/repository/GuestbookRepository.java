package nhn.rookieHAMATF.ryungjinkim_guestbook.repository;

import nhn.rookieHAMATF.ryungjinkim_guestbook.entity.Guestbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

// Querydsl을 사용하기 위해서는 QuerydslPredicateExecutor를 추가로 상속한다.
public interface GuestbookRepository extends JpaRepository<Guestbook, Long>, QuerydslPredicateExecutor<Guestbook> {

}
