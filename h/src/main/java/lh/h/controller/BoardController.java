package lh.h.controller;

import jakarta.validation.Valid;
import lh.h.entity.Board;
import lh.h.interfaces.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 게시글 목록 및 검색
    @GetMapping("/blogpage")
    public String listWithPaging(Model model,
                                 @PageableDefault(page = 0, size = 9, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                 @RequestParam(required = false) String searchKeyword) {
        Page<Board> list = boardService.boardList(searchKeyword, pageable);

        int totalPages = list.getTotalPages();
        int currentPage = list.getNumber() + 1; // 페이지는 0부터 시작하므로 보정
        int maxPageNumberToShow = 5;

        //max(음수인 경우 1로 지정, start page 지정)
        int startPage = Math.max(1, currentPage - (maxPageNumberToShow / 2));
        int endPage = startPage + maxPageNumberToShow - 1;

        if (endPage > totalPages) {
            endPage = totalPages;
            startPage = endPage - 4;
        }


        model.addAttribute("boards", list);
        model.addAttribute("searchKeyword", searchKeyword);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("totalPages", totalPages);

        return "boards/blogpage";
    }

    // 게시글 작성 폼
    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("board", new Board());
        return "boards/form";
    }

    // 게시글 저장
    @PostMapping("/form")
    public String save(@Valid @ModelAttribute Board board, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "입력 값이 올바르지 않습니다. 다시 확인해주세요.");
            return "boards/form";
        }
        boardService.save(board);
        return "redirect:/boards/blogpage";
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        boardService.deleteById(id);
        return "redirect:/boards/blogpage";
    }

    // 게시글 상세보기
    @GetMapping("/{id}")
    public String viewBoard(@PathVariable Long id, Model model) {
        Board board = boardService.findById(id);
        model.addAttribute("board", board);
        return "boards/boardDetail";
    }
}
