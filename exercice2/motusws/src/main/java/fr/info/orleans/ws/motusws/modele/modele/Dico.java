package fr.info.orleans.ws.motusws.modele.modele;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Fred on 02/06/2016.
 */
public class Dico {
    public static final String DEFAULT_FILENAME = "dicosimple7lettres";
    private volatile static Map<String, Dico> instanceByFile = new HashMap<>();
    private List<String> list = new ArrayList<>();

    private Dico(String filePath) {
        BufferedReader buffer = null;
//        filePath = (Dico.class).getResource(filePath+".txt").getFile();
        //   = new BufferedReader(new FileReader(filePath));

        InputStream in = (InputStream) (Dico.class.getResourceAsStream(filePath+".txt"));
        buffer = new BufferedReader(new InputStreamReader(in));
        try (Stream<String> stream = buffer.lines()) {
            list = stream
                    .map(String::toUpperCase)
                    .collect(Collectors.toList());
        }
    }

    public static Dico getInstance(String filePath) {
        if (!instanceByFile.containsKey(filePath)) {
            synchronized (Dico.class) {
                if (!instanceByFile.containsKey(filePath)) {
                    instanceByFile.put(filePath, new Dico(filePath));
                }
            }
        }
        return instanceByFile.get(filePath);
    }

    public String getRandomMot() {
        int num = (int) (Math.random() * list.size());
        return list.get(num);
    }

    public boolean isMot(String mot) {
        return list.contains(mot);
    }

    public List<String> getList() {
        return list;
    }
}
