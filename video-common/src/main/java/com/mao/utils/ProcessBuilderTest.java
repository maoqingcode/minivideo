package com.mao.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ProcessBuilderTest {
    public static void main(String[] args) throws Exception{
        ArrayList<String> command=new ArrayList<>();
        command.add("D:\\CodeTools\\ffmpeg\\bin\\ffmpeg.exe");
        command.add("-i");
        command.add("D:\\CodeTools\\ffmpeg\\bin\\test2.mp4");
        command.add("-i");
       // command.add("D:\\CodeTools\\ffmpeg\\bin\\music.mp4");
        command.add("-t");
        command.add(String.valueOf(9));

        command.add("-y");
        command.add("D:\\CodeTools\\ffmpeg\\bin\\out2.mp4");
        ProcessBuilder pd=new ProcessBuilder(command);
        Process process=  pd.start();
        InputStream error=    process.getErrorStream();
        System.out.println("error: "+error);
        InputStream in=process.getInputStream();

        BufferedReader reader=new BufferedReader(new InputStreamReader(in));
        String line="";
        while((line=reader.readLine())!=null){
            System.out.println(line);
        }
    }
}
