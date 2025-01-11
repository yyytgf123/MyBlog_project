package lh.h.controller;

import lh.h.entity.Board;
import lh.h.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    //board main
    @GetMapping("/blogpage")
    public String list(Model model) {
        model.addAttribute("boards", boardService.findAll());
        return "boards/blogpage";
    }

    //board write get
    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("board", new Board());
        return "boards/form";
    }

    //board write post
    @PostMapping("/form")
    public String save(@ModelAttribute Board board){
        boardService.save(board);
        return "redirect:/boards/blogpage";
    }

    //board article delete
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        System.out.println("Request to delete board with ID: " + id);
        boardService.deleteById(id);
        return "redirect:/boards/blogpage";
    }

    //board article detail
    @GetMapping("/{id}")
    public String viewBoard(@PathVariable Long id, Model model) {
        Board board = boardService.findById(id);
        model.addAttribute("board", board);
        return "boards/boardDetail";
    }
}
