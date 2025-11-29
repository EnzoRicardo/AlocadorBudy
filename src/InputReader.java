import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InputReader {
    public ProgramEntry[] readFile(String fileName) {
        int count = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line = br.readLine();
            while (line != null) {
                count = count + 1;
                line = br.readLine();
            }
            br.close();
        } catch (IOException e){
            System.out.println("Erro lendo o arquivo..");
        }

        ProgramEntry[] list = new ProgramEntry[count];

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line = br.readLine();
            int index = 0;

            while(line != null){
                String[] parts = line.split("\\s+");
                char label = parts[0].charAt(0);
                int sizeKB = Integer.parseInt(parts[1]);

                ProgramEntry p = new ProgramEntry(label, sizeKB);
                list[index] = p;
                index = index + 1;

                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Erro lendo o arquivo..");
        }

        return list;
    }
}

//tem trycatch neste arquivo pois esta separado da logica do alocador, onde esta proibido utilizar
