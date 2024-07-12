package jpabook.jpashop.controller;

import jpabook.jpashop.service.login.LoginSuccessValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;

@Controller
public class LoginController {

    @Autowired
    LoginSuccessValidator loginSuccessValidator;




    @GetMapping("/login/login")
    public String login(HttpSession session, Model model,HttpServletRequest request) throws UnsupportedEncodingException {
       // naverLoginAPI(session,model);
        //String referrer = request.getHeader("Referer");
       // request.getSession().setAttribute("prePage", referrer);
        return "login/login";
    }

//    @Value("${naver.client.id}")
//    String naverClientId;
//    @Value("${naver.client.secret}")
//    String naverCliendSecret;
//    @Value("${naver.redirect.uri}")
//    String naverRedirectUri;

//    private void naverLoginAPI(HttpSession session, Model model) throws UnsupportedEncodingException {
//        String clientId =  naverClientId;   //애플리케이션 클라이언트 아이디값";    "YOUR clientId"
//        String redirectURI = URLEncoder.encode(naverRedirectUri, "UTF-8");   // 이것역시   "Your redirect URI"
//        SecureRandom random = new SecureRandom();
//        String state = new BigInteger(130, random).toString();
//        String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
//        apiURL += "&client_id=" + clientId;
//        apiURL += "&redirect_uri=" + redirectURI;
//        apiURL += "&state=" + state;
//        session.setAttribute("state", state);
//        model.addAttribute("naverAPIURL",apiURL);
//    }
//
//    @RequestMapping("naver/post/login")
//    public String naverPostLogin(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
//        String clientId = naverClientId;   //애플리케이션 클라이언트 아이디값";
//        String clientSecret = naverCliendSecret;//애플리케이션 클라이언트 시크릿값";
//        String code = request.getParameter("code");
//        String state = request.getParameter("state");
//        String redirectURI = URLEncoder.encode(naverRedirectUri ,"UTF-8");
//        String apiURL;
//        apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
//        apiURL += "client_id=" + clientId;
//        apiURL += "&client_secret=" + clientSecret;
//        apiURL += "&redirect_uri=" + redirectURI;
//        apiURL += "&code=" + code;
//        apiURL += "&state=" + state;
//        String access_token = "";
//        String refresh_token = "";
//        System.out.println("apiURL="+apiURL);
//        try {
//            URL url = new URL(apiURL);
//            HttpURLConnection con = (HttpURLConnection)url.openConnection();
//            con.setRequestMethod("GET");
//            int responseCode = con.getResponseCode();
//            BufferedReader br;
//            System.out.print("responseCode="+responseCode);
//            if(responseCode==200) { // 정상 호출
//                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
//            } else {  // 에러 발생
//                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
//            }
//            String inputLine;
//            StringBuffer res = new StringBuffer();
//            while ((inputLine = br.readLine()) != null) {
//                res.append(inputLine);
//            }
//            br.close();
//            if(responseCode==200) {
//                PrintWriter out=response.getWriter();
//                out.println(res.toString());
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//        return "home";  //일단 무조건 home으로 가도록    아직 redirect아님
//    }



}
