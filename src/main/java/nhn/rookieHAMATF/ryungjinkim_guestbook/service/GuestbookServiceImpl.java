package nhn.rookieHAMATF.ryungjinkim_guestbook.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nhn.rookieHAMATF.ryungjinkim_guestbook.dto.GuestbookDTO;
import nhn.rookieHAMATF.ryungjinkim_guestbook.dto.PageRequestDTO;
import nhn.rookieHAMATF.ryungjinkim_guestbook.dto.PageResultDTO;
import nhn.rookieHAMATF.ryungjinkim_guestbook.entity.Guestbook;
import nhn.rookieHAMATF.ryungjinkim_guestbook.entity.QGuestbook;
import nhn.rookieHAMATF.ryungjinkim_guestbook.repository.GuestbookRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor // Lombok 의존성 자동 주입(final로 선언한 변수)
public class GuestbookServiceImpl implements GuestbookService{
    private final GuestbookRepository repository;
    @Override
    public Long register(GuestbookDTO dto) {
        log.info("DTO--------------------------");
        log.info(dto);

        Guestbook entity = dtoToEntity(dto);

        log.info(entity);
        repository.save(entity);
        return entity.getGno();
    }

    @Override
    public GuestbookDTO read(Long gno){
        Optional<Guestbook> result = repository.findById(gno);
        return result.isPresent()? entityToDTO(result.get()) : null;
    }

    @Override
    public void remove(Long gno) {
        repository.deleteById(gno);
    }

    @Override
    public void modify(GuestbookDTO dto) {
        Optional<Guestbook> result = repository.findById(dto.getGno());
        if(result.isPresent()){
            Guestbook entity = result.get();
            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());
            repository.save(entity);
        }
    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());
        BooleanBuilder booleanBuilder = getSearch(requestDTO); // 새로 추가된 구
        Page<Guestbook> result = repository.findAll(booleanBuilder, pageable); // querydsl 사용
        Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDTO(entity));
        return new PageResultDTO<GuestbookDTO, Guestbook>(result, fn);
    }
    // Querydsl 처리
    private BooleanBuilder getSearch(PageRequestDTO requestDTO){
        String type = requestDTO.getType();
        String keyword = requestDTO.getKeyword();

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QGuestbook qGuestbook = QGuestbook.guestbook;
        BooleanExpression expression = qGuestbook.gno.gt(0L);
        booleanBuilder.and(expression);

        if(type == null || type.trim().length() == 0){
            return booleanBuilder;
        }

        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if(type.contains("t")){
            conditionBuilder.or(qGuestbook.title.contains(keyword));
        }
        if(type.contains("c")){
            conditionBuilder.or(qGuestbook.content.contains(keyword));
        }
        if(type.contains("w")){
            conditionBuilder.or(qGuestbook.writer.contains(keyword));
        }
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }
}
