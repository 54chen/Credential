package com.john.biz;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.john.common.EncryptUtil;
import com.john.common.FileUtil;

public class Verifier {

   public boolean bind(String user, String password) throws IOException {

      String existedUser = FileUtil.getUserStoredSalt(user);
      boolean result = true;
      if (existedUser != "") {
         return false;
      }
   
      List<String> dicts = FileUtil.readDictFromFile();
      for (String d : dicts) {
         if (d.equals(password)) {
            return false;
         }
      }

      String salt = EncryptUtil.genSalt(password);
      String hash = EncryptUtil.bcrypt(password, salt);

      FileUtil.saveUserToSaltFile(user, salt);
      FileUtil.saveUserToPwdFile(user, hash, "0");
      return result;
   }

   public String authenticate(String user, String password) throws IOException {
      Map<String, String> existedUser = FileUtil.getSingleUserFromPwdFile(user);
      //String salt = FileUtil.getUserStoredSalt(user);
      int status  = Integer.parseInt(existedUser.get(FileUtil.KEY_STATUS).trim());
      if (status >= 100) {
         return "This username is locked!";
      }
      String hash = existedUser.get(FileUtil.KEY_HASH);
      if (!EncryptUtil.check(password, hash)) {
         FileUtil.saveUserToPwdFile(user, hash, ""+ ++status);
         return "false";
      } else {
         FileUtil.saveUserToPwdFile(user, hash, "0");
      }

      return "true";
   }
}
