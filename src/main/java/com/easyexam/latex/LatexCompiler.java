package com.easyexam.latex;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.springframework.stereotype.Component;

@Component
public class LatexCompiler {

  public final String BASE_PATH = "tex/files/";

  public static byte[] toByteArray(InputStream in) throws IOException {

    ByteArrayOutputStream os = new ByteArrayOutputStream();

    byte[] buffer = new byte[1024];
    int len;

    while ((len = in.read(buffer)) != -1) {
      os.write(buffer, 0, len);
    }

    return os.toByteArray();
  }

  private void writeToFile(String input, String filename) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(BASE_PATH + filename));
    writer.write(input);
    writer.close();
  }

  public void compile(String latexString, String filename) {
    String s;

    try {
      this.writeToFile(latexString, filename);
      String command = "/usr/bin/pdflatex " + filename;
      Process process = Runtime.getRuntime().exec(command);

      BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
      BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));

      System.out.println("Standard IN:\n");
      while ((s = input.readLine()) != null) {
        System.out.println(s);
      }

      System.out.println("Standard ERROR:\n");
      while ((s = error.readLine()) != null) {
        System.out.println(s);
      }

    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(-1);
    }
  }
}
