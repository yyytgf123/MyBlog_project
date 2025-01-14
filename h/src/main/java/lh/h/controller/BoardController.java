package lh.h.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lh.h.entity.Board;
import lh.h.interfaces.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.IllegalFormatCodePointException;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    /* Board list, paging, search ------------------------------------------------------------------------------------*/
    @GetMapping("/blogpage")
    public String listWithPaging(Model model,
                                 @PageableDefault(page = 0, size = 9, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                 //아래에서 받아온 정보를 토대로 RequestParam을 통해서 정보를 html로 전송해줌
                                 @RequestParam(required = false)
                                     //html에서 search부분에 name과 동일한 매개변수를 써줘야됌, 그래야지 정보를 받아옴
                                     String searchKeyword) {
        Page<Board> list = boardService.boardList(pageable);

        //----------------------------------- Search ---------------------------------------//
        if (searchKeyword == null) {

            list = boardService.boardList(pageable);
        } else {
            list = boardService.boardSearchList(searchKeyword, pageable);
        }
        //---------------------------------------------------------------------------------//


        //----------------------------------- Paging --------------------------------------//
        int totalPages = list.getTotalPages();
        int currentPage = list.getNumber() + 1; //tm는 0부터 시작해서 +1해줘야지 1페이지 위치
        int maxPageNumberToShow = 5;

        int startPage = Math.max(1, currentPage - (maxPageNumberToShow / 2));
        int endPage = startPage + maxPageNumberToShow - 1;

        if (endPage > totalPages) {
            endPage = totalPages;
            startPage = Math.max(1, endPage - (maxPageNumberToShow - 1));
        }

        model.addAttribute("searchKeyword", searchKeyword);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPages", totalPages);
        //---------------------------------------------------------------------------------//

        //----------------------------------- List ----------------------------------------//
        model.addAttribute("boards", list);
        //---------------------------------------------------------------------------------//


        return "boards/blogpage";
    }
    /*----------------------------------------------------------------------------------------------------------------*/


    /* 게시글 작성 화면 -------------------------------------------------------------------------------------------------*/
    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("board", new Board());
        return "boards/form";
    }
    /*----------------------------------------------------------------------------------------------------------------*/

    /* 게시글 작성(file upload + write + errormessage) -----------------------------------------------------------------*/
    @PostMapping("/form")
    public String save(@Valid @ModelAttribute Board board, BindingResult bindingResult, Model model,
                       //html로 전송, value = "file"로 html에서 사진 file을 받아옴
                       @RequestParam("title") String title,
                       @RequestParam("content") String content,
                       @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {

        //------------------------------ title not null -----------------------------------//
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "입력 값이 올바르지 않습니다. 다시 확인해주세요.");
            return "boards/form";
        }
        //---------------------------------------------------------------------------------//

        //-------------------------------- file upload ------------------------------------//
        boardService.saveFile(board, file);

        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/blog/blogpage");
        //---------------------------------------------------------------------------------//

        return "redirect:/boards/blogpage";
    }
    /*----------------------------------------------------------------------------------------------------------------*/


    /* 게시글 업데이트 페이지 이동 ----------------------------------------------------------------------------------------*/
    @GetMapping("/boardDetail/boardUpdate/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        // 게시글 정보 가져오기'
        Board board = boardService.findById(id);

        // 게시글 정보를 모델에 추가
        model.addAttribute("board", board);

        // boardUpdate.html로 이동
        return "boards/boardUpdate";
    }
    /*----------------------------------------------------------------------------------------------------------------*/

    /* 게시글 업데이트 --------------------------------------------------------------------------------------------------*/
    @PostMapping("/update/{id}")
    public String updateBoard(
                            @PathVariable Long id,
                            @Valid @ModelAttribute Board updatedBoard,
                            BindingResult bindingResult,
                            @RequestParam(value = "file", required = false) MultipartFile file,
                            Model model) throws IOException {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "입력 값이 올바르지 않습니다. 다시 확인해주세요.");
            return "boards/updateForm";
        }

        boardService.updateBoard(id, updatedBoard, file);

        return "redirect:/boards/blogpage";
    }
    /*----------------------------------------------------------------------------------------------------------------*/


    /* 게시글 삭제 -----------------------------------------------------------------------------------------------------*/
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        boardService.deleteById(id);
        return "redirect:/boards/blogpage";
    }
    /*----------------------------------------------------------------------------------------------------------------*/

    /* 게시글 상세보기 --------------------------------------------------------------------------------------------------*/
    @GetMapping("/{id}")
    public String viewBoard(@PathVariable Long id, Model model) {
        Board board = boardService.findById(id);
        model.addAttribute("board", board);
        return "boards/boardDetail";
    }
    /*----------------------------------------------------------------------------------------------------------------*/
}
