import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;

public class FileRenamer {

    public static void main(String[] args) {
        if (args.length < 5) {
            printUsage();
            return;
        }

        Path rootPath = Paths.get(args[0]);          // 1. 경로
        String type = args[1].toLowerCase();         // 2. file / dir
        String pos = args[2].toLowerCase();          // 3. pre / suf / con (위치)
        String patternRaw = args[3];                 // 4. old=new
        List<String> extensions = Arrays.asList(args[4].split(",")); // 5. 확장자

        String[] parts = patternRaw.split("=");
        if (parts.length != 2) {
            System.out.println("패턴 형식 오류: old=new");
            return;
        }
        String target = parts[0];
        String replacement = parts[1];

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(rootPath)) {
            for (Path entry : stream) {
                boolean isDir = Files.isDirectory(entry);
                String name = entry.getFileName().toString();

                if (type.equals("dir") && isDir) {
                    process(entry, name, pos, target, replacement);
                } else if (type.equals("file") && !isDir) {
                    if (hasMatchingExtension(name, extensions)) {
                        process(entry, name, pos, target, replacement);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void process(Path entry, String name, String pos, String target, String rep) throws IOException {
        String newName = name;

        // 위치 옵션에 따른 분기
        switch (pos) {
            case "pre" -> { if (name.startsWith(target)) newName = rep + name.substring(target.length()); }
            case "suf" -> {
                // 확장자 제외 파일명만 처리하려면 추가 로직 필요, 여기서는 전체 명칭 기준
                if (name.endsWith(target)) newName = name.substring(0, name.length() - target.length()) + rep;
            }
            case "con" -> { if (name.contains(target)) newName = name.replace(target, rep); }
        }

        if (!newName.equals(name)) {
            Files.move(entry, entry.resolveSibling(newName));
            System.out.println("[변경] " + name + " -> " + newName);
        }
    }

    private static boolean hasMatchingExtension(String fileName, List<String> extensions) {
        return extensions.stream().anyMatch(ext -> fileName.toLowerCase().endsWith("." + ext.toLowerCase()));
    }

    private static void printUsage() {
        System.out.println("사용법: java FileRenamer [경로] [file|dir] [pre|suf|con] [기존=새] [확장자]");
        System.out.println(" - pre: 시작부분, suf: 끝부분, con: 포함문자");
    }
}