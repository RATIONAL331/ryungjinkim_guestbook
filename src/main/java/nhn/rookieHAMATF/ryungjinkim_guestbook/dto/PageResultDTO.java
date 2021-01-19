package nhn.rookieHAMATF.ryungjinkim_guestbook.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO, ENTITY>{
    private List<DTO> dtoList;

    private int totalPage;
    private int page;
    private int size;
    private int start, end;
    private boolean prev, next;
    private List<Integer> pageList;

    public PageResultDTO(Page<ENTITY> result, Function<ENTITY, DTO> fn){
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }
    private void makePageList(Pageable pageable){
        this.page = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();

        int tmpEnd = (int)(Math.ceil(page/10.0)) * 10;
        this.start = tmpEnd - 9;
        prev = start > 1;
        end = totalPage > tmpEnd ? tmpEnd : totalPage;
        next = totalPage > tmpEnd;

        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }
}
