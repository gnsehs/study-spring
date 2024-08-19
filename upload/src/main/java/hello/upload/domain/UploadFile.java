package hello.upload.domain;


import lombok.Data;

@Data
public class UploadFile {

    private String uploadFileName; // 사용자가 입력한 파일 이름
    private String storeFileName; // 서버 스토리지에 저장되는 파일 이름 -> 중복 방지

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
