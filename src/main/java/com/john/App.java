package com.john;

import java.io.IOException;

import com.john.biz.SwearChecker;
import com.john.biz.Verifier;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

/**
 * Credential APP v0.1
 *
 */
public class App {
    private static SwearChecker swearChecker = new SwearChecker();
    private static Verifier verifier = new Verifier();

    public static void main(String[] args) {
        TextIO textIO = TextIoFactory.getTextIO();

        int step = 0;
        showMsg(textIO, "Credential APP v0.1");

        while (step != 3) {
            step = textIO.newIntInputReader().withMinVal(1).withMaxVal(3)
                    .read("\nPlease choose: 1.Enrolment 2.Verification 3.Exit");
            switch (step) {
                case 1:
                    enrolment(textIO);
                    break;
                case 2:
                    verification(textIO);
                    break;
                default:
                    break;
            }
        }
        showMsg(textIO, "Bye!");
    }

    private static void enrolment(TextIO textIO) {

        String user = textIO.newStringInputReader().withMaxLength(50).withPattern("[a-zA-Z0-9_]*")
                .withValueChecker(swearChecker)
                .read("Username");

        String password = textIO.newStringInputReader()
                .withMinLength(8).withMaxLength(64)
                .withInputMasking(true)
                .read("Password");

        String repeatPassword = textIO.newStringInputReader()
                .withMinLength(8).withMaxLength(64)
                .withInputMasking(true)
                .read("Repeat Password");

        if (!password.equals(repeatPassword)) {
            showMsg(textIO, "Two passwords do not match!");
            return;
        }
        try {
            if (verifier.bind(user, password)) {
                showMsg(textIO, "Register successfully! ");
            } else {
                showMsg(textIO, "Registration failed! ");
            }
        } catch (IOException e) {
            showMsg(textIO, "Register failed! " + e.getMessage());
        }
    }

    private static void verification(TextIO textIO) {
        String user = textIO.newStringInputReader().withMaxLength(50).withPattern("[a-zA-Z0-9_]*")
                .withValueChecker(swearChecker)
                .read("Username");

        String password = textIO.newStringInputReader()
                .withMinLength(8).withMaxLength(64)
                .withInputMasking(true)
                .read("Password");
  
        try {
            String result = verifier.authenticate(user, password);
            if (result=="true") {
                showMsg(textIO, "Welcome! Authenticate successfully! ");
            } else if(result == "false") {
                showMsg(textIO, "User or password don't match!");
            }else{
                showMsg(textIO, result);
            }
        } catch (IOException e) {
            showMsg(textIO, "System error, please check the permission! " + e.getMessage());
        }
    }

    private static void showMsg(TextIO textIO, String msg) {
        textIO.getTextTerminal().println("/======================================\\");
        textIO.getTextTerminal().println("        " + msg + "         ");
        textIO.getTextTerminal().println("\\======================================/");
    }
}
