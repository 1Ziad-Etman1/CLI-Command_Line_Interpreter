import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.stream.Stream;
public class Terminal {
    Parser parser;

    public Terminal() {
        this.parser = new Parser();
    }

    //Implement each command in a method, for example:
    
    public String pwd() {
        return System.getProperty("user.dir");
    }
    public String echo(){
        return parser.args[0];
    }

    public void cd() {
        Path newPath = Paths.get(parser.args[0]);
        Path CWD = Paths.get(System.getProperty("user.dir"));
        if (parser.args[0] == ".."){
            CWD = CWD.getParent();
            System.setProperty("user.dir", CWD.toString());
        } else {
            if(Files.exists(newPath) && Files.isDirectory(newPath)){
                newPath = newPath.toAbsolutePath();
                String absPath = newPath.toString();
                System.setProperty("user.dir", absPath);
            } else {
                System.out.println("Invalid working dir!");
            }
        }


    }

    public void ls(){
        try {
            // Get the current working directory
            Path CWD = Paths.get(System.getProperty("user.dir"));

            // List the files in the current working directory
            try (Stream<Path> files = Files.list(CWD)) {
                files.forEach(System.out::println);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mkdir(){
        try {
            Path targetPath = Paths.get(parser.args[0]);
            Files.createDirectories(targetPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rmdir(){
        try {
            Path targetPath = Paths.get(parser.args[0]);
            Files.deleteIfExists(targetPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void touch(){
        try {
            Path targetPath = Paths.get(parser.args[0]);
            Files.createFile(targetPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rm(){
        try {
            Path targetPath = Paths.get(parser.args[0]);
            Files.deleteIfExists(targetPath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void chooseCommandAction() {
        if ((parser.commandName).equals("pwd")){
            System.out.println(pwd());
        } else if ((parser.commandName).equals("echo")){
            System.out.println(echo());
        } else if ((parser.commandName).equals("cd")) {
            cd();
        } else if ((parser.commandName).equals("ls")) {
            ls();
        } else if ((parser.commandName).equals("mkdir")) {
            mkdir();
        } else if ((parser.commandName).equals("rmdir")) {
            rmdir();
        } else if ((parser.commandName).equals("touch")) {
            touch();
        } else if ((parser.commandName).equals("rm")) {
            rm();
        }
    }

    public void main() {
        Scanner in = new Scanner(System.in);
        String input ;
        Path path = Paths.get("");
        while (true){
            System.out.print("[" + System.getProperty("user.name") + " " + path.getFileName().toString() + "] ");
            input = in.nextLine();
            if (parser.parse(input)){
                chooseCommandAction();
            }
        }
    }

}
