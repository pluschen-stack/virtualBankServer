package com.secretLab.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 证书工具,提供检验证书有效性和获取证书公钥的功能
 */
public class CertificateUtils {

    private static String getHashed(String msg){
        return DigestUtils.sha1Hex(msg);
    }

    /**
     * 获取CA证书内容
     * @return CA证书内容
     */
    public static String getCA(String path){
        StringBuilder stringBuilder = new StringBuilder();
        if(path!=null){
            File file = new File(path);
            try {
                FileInputStream fis = new FileInputStream(file);
                BufferedReader bf = new BufferedReader(new InputStreamReader(fis));
                String temp;
                int lineCount = 0;//用于计数行数
                while ((temp=bf.readLine())!=null){
                    lineCount++;
                    //真醉了，愿天堂没有中文编码
                    stringBuilder.append(temp).append("\n");
                }
                if(lineCount != 9){
                    throw new RuntimeException("error:the number of CA lines is not equal nine");
                }
                bf.close();
                fis.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 检验下级CA内容是否是上级CA签署的
     * @param superiorCA 上级CA内容，其中每一行应该以换行符分割
     * @param inferiorCA 下级CA内容，其中每一行应该以换行符分割
     * @param CAUser CA的使用者(区分大小写）
     * @return 如果是返回true，否则返回false
     */
    public static boolean isValid(String superiorCA,String inferiorCA,String CAUser){
        if(isCA(inferiorCA,CAUser)){
            String[] superiorCALines = superiorCA.split("\\n");
            String[] inferiorCALines = inferiorCA.split("\\n");
            try {
                String rootPubKey = superiorCALines[7].split(":")[1];
                String sign = inferiorCALines[8].split(":")[1];
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < 8; i++) {
                    stringBuilder.append(inferiorCALines[i]).append("\n");
                }
                return superiorCALines[3].split(":")[1].equals(inferiorCALines[3].split(":")[1]) &&
                    getHashed(stringBuilder.toString()).equals(RSATools.PublicKeyDecrypt(rootPubKey,sign));
            } catch (Exception e){
                e.printStackTrace();
            }
            return false;
        }
        return false;
    }

    /**
     * 这里面检查CA的内容是否合法，主要检查使用者和使用期限
     * @param inferiorCA 下级CA内容
     * @param CAUser CA的使用者(区分大小写）
     * @return 如果合法返回true，否则返回false
     */
    private static boolean isCA(String inferiorCA,String CAUser){
        String[] lines = inferiorCA.split("\\n");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy MM dd HH mm ss");
        LocalDateTime startTime = LocalDateTime.parse(lines[4].split(":")[1], dateTimeFormatter);
        LocalDateTime endTime = LocalDateTime.parse(lines[5].split(":")[1], dateTimeFormatter);
        Duration duration = Duration.between(startTime,endTime);
        return duration.getSeconds()>0 && CAUser.equals(lines[6].split(":")[1]);
    }
}
