package hello.upload.controller;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ItemForm {
    private Long id;
    private String itemName;
    private MultipartFile attachFile; // 첨부파일 1개
    private List<MultipartFile> imageFiles; // 이미지는 여러장
}
