package jpabook.jpashop.controller;

import jpabook.jpashop.dto.AttachDto;
import jpabook.jpashop.service.AttachService;
import jpabook.jpashop.service.FileService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
@RequiredArgsConstructor
public class AttachController {

    @Value("${file.upload.path}")
    private String uploadPath;
    private final FileService fileService;
    private final AttachService attachService;

    // @PathVariable 사용하여 url상의 경로를 변수에 할당 "/attach/download/25625"
    @RequestMapping("/attach/download")
    public void process(Long id, HttpServletResponse resp) throws Exception {
        try {
            // 서비스를 통해 첨부파일 가져오기
            AttachDto attachDto = attachService.getAttachDto(id);
            // 파일명에 한글이 있는경우 처리
            String originalName = new String(attachDto.getAttachOriginalName().getBytes("utf-8"), "iso-8859-1");
            String filePath = uploadPath + File.separatorChar + attachDto.getAttachPath();
            String fileName = attachDto.getAttachFileName();
            // 경로에 있는 파일 찾기
            File f = new File(filePath, fileName);
            if (!f.isFile()) {
                throw new Exception("해당 첨부파일이 존재하지 않습니다.");
            }
            // 다운로드를 위한 헤더 생성
            resp.setHeader("Content-Type", "application/octet-stream;");
            resp.setHeader("Content-Disposition", "attachment;filename=\"" + originalName + "\";");
            resp.setHeader("Content-Transfer-Encoding", "binary;");
            resp.setContentLength((int) f.length()); // 진행율
            resp.setHeader("Pragma", "no-cache;");
            resp.setHeader("Expires", "-1;");
            // 저장된 파일을 응답객체의 스트림으로 내보내기, resp의 outputStream에 해당파일을 복사
            FileUtils.copyFile(f, resp.getOutputStream());
            resp.getOutputStream().close();
        }catch (IOException e) {
            resp.reset();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
        }
        catch (Exception e) {
            printMessage(resp, "해당 첨부파일이 존재하지 않습니다.");
        }
    }

    // 정상적인 다운로드가 안될 경우 메시지 처리
    private void printMessage(HttpServletResponse resp, String msg) throws Exception {
        resp.setCharacterEncoding("utf-8");
        resp.setHeader("Content-Type", "text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        // target이 지정되지 않은 경우 history.back() 으로 처리
        out.println("<script type='text/javascript'>");
        out.println(" alert('" + msg + "');");
        out.println(" self.close();");
        out.println("</script>");
        out.println("<h4>첨부파일 문제 " + msg + "</h4>");
        out.println("<button onclick='self.close()'>닫기</button>");
    }

}
