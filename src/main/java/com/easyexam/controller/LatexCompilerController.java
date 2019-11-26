package com.easyexam.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.easyexam.latex.LatexCompiler;
import com.easyexam.message.request.CompileExamForm;
import com.easyexam.model.Course;
import com.easyexam.model.Question;
import com.easyexam.model.User;
import com.easyexam.repository.CourseRepository;
import com.easyexam.repository.ExamRepository;
import com.easyexam.security.jwt.JwtUtils;
import com.easyexam.security.utils.AuthenticationUtils;

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

    @Autowired
    LatexCompiler latexCompiler;

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	ExamRepository examRepository;

	@Autowired
	AuthenticationUtils authenticationUtils;

	@Autowired
	JwtUtils jwtUtils;

    @PostMapping("/compile")
	@PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
    public ResponseEntity<?> compileLatex(@Valid @RequestBody String latexString) {

        ResponseEntity<?> respEntity = null;

        String filename = "compile.tex";
        latexCompiler.compile(latexString.replaceAll("\\\\n", "\n"), filename);

        File result = new File(latexCompiler.BASE_PATH + filename);

        if (result.exists()) {
            try {
                InputStream inputStream = new FileInputStream(
                        latexCompiler.BASE_PATH + filename.replace(".tex", ".pdf"));
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
            respEntity = new ResponseEntity<String>("File Not Found. There was an error during compilation.",
                    HttpStatus.OK);
        }

        return respEntity;
    }

    @PostMapping("/compile/exam")
	@PreAuthorize("hasRole('STUDENT') or hasRole('TEACHER')")
    public ResponseEntity<?> compileLatexExam(@Valid @RequestBody CompileExamForm form) {
        ResponseEntity<?> respEntity = null;

        String filename = "compile.tex";
        String title = form.getTitle();
        List<Question> questions = form.getQuestions();
        String newLine = System.getProperty("line.separator");
        Optional<Course> course = courseRepository.findById(form.getCourseId());
        User user = authenticationUtils.getUserObject();

        String latexString = String.join(newLine,
                "\\documentclass{article}",
                       "\\title{" + title + "}",
                       "\\author{" + user.getFirstName() + " " + user.getLastName() + "}",
                       "\\begin{document}",
                "\\maketitle");

        if (course.isPresent()) {
           latexString = String.join(newLine, 
                   latexString,
                   "\\begin{center}",
                    course.get().getName() + " - " + course.get().getCode(),
                    "\\end{center}");
        }
        if (questions.size() > 0) {
            latexString = String.join(newLine,
                    latexString,
                    "\\begin{enumerate}");
            for (Question q : questions) {
                latexString = String.join(newLine, latexString, "\\item " + q.getContent());
            }

            latexString = String.join(newLine,
                    latexString,
                    "\\end{enumerate}");
        }
        latexString = String.join(newLine,
                latexString,
                "\\end{document}");
        

        latexCompiler.compile(latexString.replaceAll("\\\\n", "\n"), filename);

        File result = new File(latexCompiler.BASE_PATH + filename);

        if (result.exists()) {
            try {
                InputStream inputStream = new FileInputStream(
                        latexCompiler.BASE_PATH + filename.replace(".tex", ".pdf"));
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
            respEntity = new ResponseEntity<String>("File Not Found. There was an error during compilation.",
                    HttpStatus.OK);
        }

        return respEntity;
    }
}
