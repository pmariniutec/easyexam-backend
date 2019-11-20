package com.easyexam.controller;

import com.easyexam.latex.LatexCompiler;
import com.easyexam.security.jwt.JwtUtils;
import java.io.*;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/latex")
public class LatexCompilerController {

  @Autowired LatexCompiler latexCompiler;
  @Autowired JwtUtils jwtUtils;

  @PostMapping("/compile")
  @PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
  public ResponseEntity<?> compileLatex(@Valid @RequestBody String latexString) {

    ResponseEntity<?> respEntity = null;

    String filename = "compile.tex";
    latexCompiler.compile(latexString, filename);

    File result = new File(latexCompiler.BASE_PATH + filename);

    if (result.exists()) {
      try {
        InputStream inputStream = new FileInputStream(latexCompiler.BASE_PATH + filename);
        String type = result.toURI().toURL().openConnection().guessContentTypeFromName(filename);

        byte[] out = LatexCompiler.toByteArray(inputStream);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("content-disposition", "attachment; filename=" + filename);
        responseHeaders.add("Content-Type", type);

        respEntity = new ResponseEntity<byte[]>(out, responseHeaders, HttpStatus.OK);
      } catch (Exception e) {
        System.out.println(e);
      }
    } else {
      respEntity =
          new ResponseEntity<String>(
              "File Not Found. There was an error during compilation.", HttpStatus.OK);
    }

    return respEntity;
  }
}
