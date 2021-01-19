package nhn.rookieHAMATF.ryungjinkim_guestbook.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import nhn.rookieHAMATF.ryungjinkim_guestbook.entity.Guestbook;
import nhn.rookieHAMATF.ryungjinkim_guestbook.entity.QGuestbook;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestbookRepositoryTests {
    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    public void insertDummies(){
        IntStream.rangeClosed(1, 300).forEach(i->{
            Guestbook guestbook = Guestbook.builder()
                    .title("Title...." + i)
                    .content("Content...." + i)
                    .writer("user" + (i % 10))
                    .build();
            System.out.println(guestbookRepository.save(guestbook));
        });
    }

    @Test
    public void updateTest(){
        Optional<Guestbook> result = guestbookRepository.findById(300L);

        if(result.isPresent()){
            Guestbook guestbook = result.get();
            guestbook.changeTitle("Changed Title.....");
            guestbook.changeContent("Changed Content.....");

            guestbookRepository.save(guestbook);
        }
    }
    @Test
    public void testQuery1() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
        // 가장 먼저 동적으로 처리하기 위해서 Q도메인 클래스를 얻어온다. Q도메인 클래스를 이용하면 엔티티 클래스에 선언된 title, content같은 필드들을 변수로 활용할 수 있다.
        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = "1";
        // BooleanBuilder은 where문 조건들을 넣어주는 컨테이너로 간주
        BooleanBuilder builder = new BooleanBuilder();
        // 원하는 조건은 필드 값과 같이 결합해서 생성한다.
        // BooleanBuilder안에 들어가는 값은 com.querydsl.core.types.Predicate 타입이다. 절대 Java의 Predicate이 아니다.
        BooleanExpression expression = qGuestbook.title.contains(keyword);
        // 만들어진 조건은 and, or 같은 키워드와 결합한다.
        builder.and(expression);
        // QuerydslPredicateExcutor 인터페이를 상속받으면 builder, pageable을 받는 findALl()을 사용할 수 있다.
        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);
        result.stream().forEach(guestbook ->{
            System.out.println(guestbook);
        });
    }
    @Test
    public void testQuery2(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
        QGuestbook qGuestbook = QGuestbook.guestbook;
        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression exTitle = qGuestbook.title.contains(keyword);
        BooleanExpression exContent = qGuestbook.content.contains(keyword);
        // 결합
        BooleanExpression exAll = exTitle.or(exContent);
        // 추가
        builder.and(exAll);
        // BooleanExpression을 직접 추가
       builder.and(qGuestbook.gno.gt(0L));

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);
        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }

}
