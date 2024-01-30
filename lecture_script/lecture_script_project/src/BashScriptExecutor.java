import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class BashScriptExecutor {
    public static void main(String[] args) {
        try {
            // Chemin vers le script bash
            List<String> command = new ArrayList<>();
            command.add("./lib/infos_systeme.sh");

            // Création du processus
            ProcessBuilder builder = new ProcessBuilder(command);
            Process process = builder.start();

            // Lecture de la sortie standard et d'erreur
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            // Affichage de la sortie du script
            String line;
            while ((line = stdInput.readLine()) != null) {
                System.out.println(line);
            }

            // Affichage des erreurs du script, le cas échéant
            while ((line = stdError.readLine()) != null) {
                System.err.println(line);
            }

            // Attente de la fin du script
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
