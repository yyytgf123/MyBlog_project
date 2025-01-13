package lh.h.service;

import jakarta.validation.Valid;
import lh.h.entity.Board;
import lh.h.interfaces.BoardService;
import lh.h.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    /* 모든 게시글 조회 */
    @Override
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    /* ID로 게시글 조회 */
    @Override
    public Board findById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
    }

    /* form write(file upload + write) */
    /** file upload saveUrl**/
    @Override
    public void saveFile(Board board, MultipartFile file) throws IOException {
        String projectPath = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "static", "files").toString();

        UUID uuid = UUID.randomUUID();

        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath + File.separator + fileName);

        file.transferTo(saveFile);

        board.setFilename(fileName);
        board.setFilepath("/files/" + fileName);

        //게시글 정보 저장
        boardRepository.save(board);
    }

    /* 게시글 삭제 */
    @Transactional
    @Override
    public void deleteById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시판이 없습니다: " + id));
        boardRepository.delete(board);
    }

    /* board list*/
    @Override
    public Page<Board> boardList(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    /* board search */
    @Override
    public Page<Board> boardSearchList(String searchKeyword, Pageable pageable) {
        if (searchKeyword == null || searchKeyword.isBlank()) {
            return boardRepository.findAll(pageable); // 전체 목록 반환
        }
        return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }
}
