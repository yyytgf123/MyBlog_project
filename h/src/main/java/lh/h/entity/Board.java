package lh.h.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "제목은 필수 항목입니다.")
    @NotNull(message = "제목은 필수 항목입니다.")
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    private String filename;

    private String filepath;

    private String writer;

    @Column(name = "created_date", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdDate;

    @PrePersist
    public void prePersist() {
        this.createdDate = this.createdDate == null ? LocalDateTime.now() : this.createdDate;
    }
}
