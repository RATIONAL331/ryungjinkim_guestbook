package nhn.rookieHAMATF.ryungjinkim_guestbook.repository;

import nhn.rookieHAMATF.ryungjinkim_guestbook.entity.Guestbook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestbookRepository extends JpaRepository<Guestbook, Long> {
}
