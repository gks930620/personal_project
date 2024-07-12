package jpabook.jpashop.service;

import jpabook.jpashop.dto.AttachDto;
import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Getter
public class FileService {

	/** 다중 MultipartFile에서 VO 설정 및 업로드 파일 처리 후 List 리턴 */
	public List<AttachDto> getAttachListByMultiparts(MultipartFile[] boFiles, String category, String path)
			throws IOException {
		List<AttachDto> atchList = new ArrayList<AttachDto>();
		for (int i = 0; i < boFiles.length; i++) {
			MultipartFile multipart = boFiles[i];
			AttachDto attachDto = this.getAttachByMultipart(multipart, category, path);
			if (attachDto != null) {
				atchList.add(attachDto);
			}
		}
		return atchList;
	}

	@Value("${file.upload.path}")
	private String uploadPath;

	public AttachDto getAttachByMultipart(MultipartFile multipart, String category, String path) throws IOException {
		if (!multipart.isEmpty()) {
			String fileName = UUID.randomUUID().toString();
			AttachDto attachDto = new AttachDto();

			attachDto.setAttachOriginalName(multipart.getOriginalFilename());
			attachDto.setAttachFileSize(multipart.getSize());
			attachDto.setAttachContentType(multipart.getContentType());
			attachDto.setAttachFileName(fileName);
			attachDto.setAttachCategory(category);
			attachDto.setAttachPath(path);
			attachDto.setAttachFancySize(fancySize(multipart.getSize())); //1024 => 1kb
			String filePath = uploadPath + File.separatorChar + attachDto.getAttachPath();

			FileUtils.copyInputStreamToFile(multipart.getInputStream(), new File(filePath, fileName));
			// this static method   upload file  in C:\workspace\\upload\idea

			return attachDto; //this DTO  is used for insert in DB(mysql)  and to download later.
		} else {
			return null;
		}
	}


	//이거 왜 만들었지?
	public String findFile(AttachDto attachDto){
		String filePath = uploadPath + File.separatorChar + attachDto.getAttachPath();
		return filePath+attachDto.getAttachFileName();
	}

	public String findFileByPathAndName(String filePath,String fileName){
		return uploadPath+File.separatorChar+filePath+File.separatorChar+fileName;
	}



	private DecimalFormat df = new DecimalFormat("#,###.0");

	private String fancySize(long size) {
		if (size < 1024) { // 1k 미만
			return size + " Bytes";
		} else if (size < (1024 * 1024)) { // 1M 미만
			return df.format(size / 1024.0) + " KB";
		} else if (size < (1024 * 1024 * 1024)) { // 1G 미만
			return df.format(size / (1024.0 * 1024.0)) + " MB";
		} else {
			return df.format(size / (1024.0 * 1024.0 * 1024.0)) + " GB";
		}
	}

}