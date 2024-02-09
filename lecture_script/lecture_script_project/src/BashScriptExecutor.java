import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class BashScriptExecutor {
    public static void main(String[] args) {
        try {

            List<String> command = new ArrayList<>();
            command.add("./lib/infos_systeme.sh");


            ProcessBuilder builder = new ProcessBuilder(command);
            Process process = builder.start();


            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));


            String line;
            while ((line = stdInput.readLine()) != null) {
                System.out.println(line);
            }


            while ((line = stdError.readLine()) != null) {
                System.err.println(line);
            }


            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
