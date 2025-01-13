package lh.h.service;

import lh.h.entity.Board;
import lh.h.interfaces.BoardService;
import lh.h.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
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
    @Transactional
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

    /* board update */
    @Override
    public void updateBoard(Long id, Board updatedBoard, MultipartFile file) throws IOException {
        Board existingBoard = boardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("게시글을 찾을 수 없습니다. ID: " + id)
        );

        // 기존 데이터 업데이트
        existingBoard.setTitle(updatedBoard.getTitle());
        existingBoard.setContent(updatedBoard.getContent());
        existingBoard.setWriter(updatedBoard.getWriter());

        // 파일 업데이트
        if (file != null && !file.isEmpty()) {
            // 기존 파일 삭제 (Optional)
            if (existingBoard.getFilepath() != null) {
                File existingFile = new File("src/main/resources/static" + existingBoard.getFilepath());
                if (existingFile.exists()) {
                    existingFile.delete();
                }
            }

            // 저장 경로 설정
            String uploadDir = Paths.get("src", "main", "resources", "static", "files").toAbsolutePath().toString();
            String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            String filePath = Paths.get(uploadDir, uniqueFilename).toString();

            // 디렉토리 생성
            File saveDir = new File(uploadDir);
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }

            // 파일 저장
            File saveFile = new File(filePath);
            file.transferTo(saveFile);

            // 업데이트된 파일 정보 저장 (URL 접근 가능 경로로 설정)
            existingBoard.setFilename(uniqueFilename);
            existingBoard.setFilepath("/files/" + uniqueFilename);
        }

        // 변경 사항 저장
        boardRepository.save(existingBoard);
    }
    /* board delete */
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
