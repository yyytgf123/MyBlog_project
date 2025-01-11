package lh.h.controller;

import jakarta.validation.Valid;
import lh.h.entity.Board;
import lh.h.service.BoardService;
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

    // board main with paging
    @GetMapping("/blogpage")
    public String listWithPaging(Model model, @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable, String searchKeyword) {

        Page<Board> list = boardService.boardList(searchKeyword, pageable);

        model.addAttribute("boards", list);
        model.addAttribute("searchKeyword", searchKeyword);
        return "boards/blogpage";
    }

    // board write get
    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("board", new Board());
        return "boards/form";
    }

    // board write post
    @PostMapping("/form")
    public String save(@Valid @ModelAttribute Board board, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            // 에러 메시지를 모델에 추가
            model.addAttribute("errorMessage", "입력 값이 올바르지 않습니다. 다시 확인해주세요.");
            return "boards/form"; // 폼 페이지로 이동
        }

        boardService.save(board);
        return "redirect:/boards/blogpage";
    }

    // board article delete
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        System.out.println("Request to delete board with ID: " + id);
        boardService.deleteById(id);
        return "redirect:/boards/blogpage";
    }

    // board article detail
    @GetMapping("/{id}")
    public String viewBoard(@PathVariable Long id, Model model) {
        Board board = boardService.findById(id);
        model.addAttribute("board", board);
        return "boards/boardDetail";
    }
}
