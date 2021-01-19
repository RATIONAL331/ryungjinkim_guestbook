package nhn.rookieHAMATF.ryungjinkim_guestbook.service;

import nhn.rookieHAMATF.ryungjinkim_guestbook.dto.GuestbookDTO;
import nhn.rookieHAMATF.ryungjinkim_guestbook.dto.PageRequestDTO;
import nhn.rookieHAMATF.ryungjinkim_guestbook.dto.PageResultDTO;
import nhn.rookieHAMATF.ryungjinkim_guestbook.entity.Guestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GuestbookServiceTests {
    @Autowired private GuestbookService service;

    @Test
    public void testRegister(){
        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("Sample Title.....")
                .content("Sample Content.....")
                .writer("user0")
                .build();

        System.out.println(service.register(guestbookDTO));
    }

    @Test
    public void testList(){
        PageRequestDTO pageResultDTO = PageRequestDTO.builder().page(1).size(10).build();
        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageResultDTO);
        System.out.println("PREV: " + resultDTO.isPrev());
        System.out.println("NEXT: " + resultDTO.isNext());
        System.out.println("TOTAL: " + resultDTO.getTotalPage());

        System.out.println("--------------------------------------");
        for(GuestbookDTO guestbookDTO : resultDTO.getDtoList()){
            System.out.println(guestbookDTO);
        }
        System.out.println("======================================");
        resultDTO.getPageList().forEach(i-> System.out.println(i));
    }

}
