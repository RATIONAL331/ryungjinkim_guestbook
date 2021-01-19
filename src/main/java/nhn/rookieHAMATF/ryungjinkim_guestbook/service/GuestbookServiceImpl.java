package nhn.rookieHAMATF.ryungjinkim_guestbook.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nhn.rookieHAMATF.ryungjinkim_guestbook.dto.GuestbookDTO;
import nhn.rookieHAMATF.ryungjinkim_guestbook.dto.PageRequestDTO;
import nhn.rookieHAMATF.ryungjinkim_guestbook.dto.PageResultDTO;
import nhn.rookieHAMATF.ryungjinkim_guestbook.entity.Guestbook;
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
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());
        Page<Guestbook> result = repository.findAll(pageable);
        Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDTO(entity));
        return new PageResultDTO<GuestbookDTO, Guestbook>(result, fn);
    }
}