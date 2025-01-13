function showFileName() {
    const fileInput = document.getElementById('photo');
    const fileNameDisplay = document.getElementById('file-name-display');

    if (fileInput.files.length > 0) {
        //업로드 파일 이름 표시
        fileNameDisplay.textContent = "선택된 파일 : " + fileInput.files[0].name;
    } else {
        fileNameDisplay.textContent = "선택된 파일 없음";
    }
}