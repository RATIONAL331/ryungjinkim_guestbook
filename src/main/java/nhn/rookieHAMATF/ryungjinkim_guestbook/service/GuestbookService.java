package nhn.rookieHAMATF.ryungjinkim_guestbook.service;

import nhn.rookieHAMATF.ryungjinkim_guestbook.dto.GuestbookDTO;
import nhn.rookieHAMATF.ryungjinkim_guestbook.dto.PageRequestDTO;
import nhn.rookieHAMATF.ryungjinkim_guestbook.dto.PageResultDTO;
import nhn.rookieHAMATF.ryungjinkim_guestbook.entity.Guestbook;

public interface GuestbookService {
    Long register(GuestbookDTO dto);
    GuestbookDTO read(Long gno);

    PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO);

    default Guestbook dtoToEntity(GuestbookDTO dto){
        Guestbook entity = Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }

    default GuestbookDTO entityToDTO(Guestbook entity){
        GuestbookDTO dto = GuestbookDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();
        return dto;
    }


}
