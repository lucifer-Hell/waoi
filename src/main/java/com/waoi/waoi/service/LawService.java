package com.waoi.waoi.service;

import com.itextpdf.html2pdf.HtmlConverter;
import com.waoi.waoi.dto.GetCaseListsDTO;
import com.waoi.waoi.utils.ApiCaller;
import com.waoi.waoi.utils.CaptchaService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.HashMap;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;
import java.io.*; // for file I/O


@Service
public class LawService {
    @Autowired
    CaptchaService captchaService;
    @Autowired
    ApiCaller apiCaller;



    @SneakyThrows
    public String getMatchedCasesList(GetCaseListsDTO getCaseListsDTO){
        // find captcha & Session id
        String response=null;
        do{
            try{
                HashMap<String,Object> captchaImageAndCookie=getCaptchaImageAndCookies();
                String captcha=captchaService.getCaptchaText((byte[]) captchaImageAndCookie.get("captcha"));
                MultiValueMap<String, String> tempBody = new LinkedMultiValueMap<String, String>();
                tempBody.add("action_code","showRecords");
                tempBody.add("state_code","18");
                tempBody.add("dist_code","1");
                tempBody.add("case_type",getCaseListsDTO.getCaseType());
                tempBody.add("case_no",getCaseListsDTO.getCaseNo());
                tempBody.add("rgyear",getCaseListsDTO.getRegYear());
                tempBody.add("caseNoType","new");
                tempBody.add("displayOldCaseNo","NO");
                tempBody.add("captcha", captcha);
                tempBody.add("court_code","1");
                HttpHeaders httpHeaders=new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                HttpHeaders tempHeader= (HttpHeaders) captchaImageAndCookie.get("headers");
                httpHeaders.set("Cookie", tempHeader.get("Set-Cookie").get(0).split(";")[0]);
                response=apiCaller.callApi(
                        "https://hcservices.ecourts.gov.in/ecourtindiaHC/cases/case_no_qry.php",
                        HttpMethod.POST,
                        httpHeaders,
                        tempBody,
                        String.class
                );
            }
            catch (ArrayIndexOutOfBoundsException e){
                response="Invalid Captcha";
            }
            System.out.println("response recieved "+response);
        }while (response.contains("Invalid Captcha"));
        response=response.replace("\uFEFF", "");
        if(response.isEmpty()){
            return "Invalid input entered";
        }
        String uCaseNo=response.split("~")[0];
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("court_code","1");
        formData.add("state_code","18");
        formData.add("dist_code","1");
        formData.add("case_no",uCaseNo);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String htmlResponse= apiCaller.callApi(
                "https://hcservices.ecourts.gov.in/ecourtindiaHC/cases/o_civil_case_history.php",
                HttpMethod.POST,
                headers,
                formData,
                String.class);
//        htmlResponse=htmlResponse.split("</script>",2)[1];
//        String xhtml=htmlToXhtml(htmlResponse);
//        xhtml="<!DOCTYPE html [\n" +
//                "    <!ENTITY nbsp \"&#160;\"> \n" +
//                "]>"+xhtml;

//        FileWriter writer = new FileWriter("case.html");
//        writer.write(htmlResponse);
//        writer.close();
//        HtmlConverter.convertToPdf(new File("./case.html"),new File("./case_3.pdf"));
//        xhtmlToPdf(xhtml,"casepdf.pdf");
        return htmlResponse;
    }
    private static void xhtmlToPdf(String xhtml, String outFileName) throws IOException {
        File output = new File(outFileName);
        ITextRenderer iTextRenderer = new ITextRenderer();
        iTextRenderer.setDocumentFromString(xhtml);
        iTextRenderer.layout();
        OutputStream os = new FileOutputStream(output);
        iTextRenderer.createPDF(os);
        os.close();
    }
    private static String htmlToXhtml(String html) {
        Document document = Jsoup.parse(html);
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        return document.html();
    }

    private HashMap<String,Object> getCaptchaImageAndCookies(){
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<byte[]> captchaImageResponse=restTemplate.getForEntity("https://hcservices.ecourts.gov.in/ecourtindiaHC/securimage/securimage_show.php",byte[].class);
        HashMap<String,Object>response=new HashMap<>();
        response.put("captcha",captchaImageResponse.getBody());
        response.put("headers",captchaImageResponse.getHeaders());
        return response;
    };

    @SneakyThrows
    private String parseTextFromCaptchaImage(byte []image){
        return captchaService.getCaptchaText(image);
    };

}
