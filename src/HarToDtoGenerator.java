import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class HarToDtoGenerator {

    private static final String PACKAGE_NAME = "com.example.biz";
    private static final String SAVE_PATH = "/Users/a60192259/Documents/";

    public static void main(String[] args) {
        String harFilePath = "/Users/a60192259/Documents/test2.har"; // 크롬에서 내보낸 HAR 파일 경로

        try {
            String content = Files.readString(Paths.get(harFilePath));
            JSONObject harJson = new JSONObject(content);
            JSONArray entries = harJson.getJSONObject("log").getJSONArray("entries");

            for (int i = 0; i < entries.length(); i++) {
                JSONObject entry = entries.getJSONObject(i);

                // 1. 요청(Payload) 처리
                if (entry.getJSONObject("request").has("postData")) {
                    String reqText = entry.getJSONObject("request").getJSONObject("postData").getString("text");
                    processJson(reqText);
                }

                // 2. 응답(Response) 처리
                JSONObject response = entry.getJSONObject("response");
                if (response.getJSONObject("content").has("text")) {
                    String resText = response.getJSONObject("content").getString("text");
                    processJson(resText);
                }
            }
        } catch (Exception e) {
            System.err.println("HAR 파일 처리 중 오류: " + e.getMessage());
        }
    }

    private static void processJson(String jsonStr) {
        try {
            if (jsonStr == null || !jsonStr.trim().startsWith("{")) return;
            JSONObject root = new JSONObject(jsonStr);

            // In/Out 키 탐색
            for (String key : root.keySet()) {
                if (key.endsWith("In") || key.endsWith("Out")) {
                    generateJavaFile(key, root);
                }
            }
        } catch (Exception e) {
            // JSON이 아닌 텍스트는 무시
        }
    }

    private static void generateJavaFile(String className, JSONObject obj) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(PACKAGE_NAME).append(";\n\n");
        sb.append("import lombok.Data;\nimport lombok.EqualsAndHashCode;\n");
        sb.append("import java.util.List;\nimport java.math.BigDecimal;\n");
        sb.append("import com.google.gson.annotations.SerializedName;\n\n");

        sb.append("@Data\n");
        if (className.endsWith("In") || className.endsWith("Out")) {
            sb.append("@EqualsAndHashCode(callSuper = false)\n");
            sb.append("public class ").append(className).append(" extends BaseCommonDto {\n\n");
        } else {
            sb.append("public class ").append(className).append(" {\n\n");
        }

        for (String key : obj.keySet()) {
            if (isCommonField(key)) continue;

            Object value = obj.get(key);
            String javaType = "String";

            if (value instanceof JSONObject) {
                javaType = key + "Dto";
                generateJavaFile(javaType, (JSONObject) value);
            } else if (value instanceof JSONArray) {
                JSONArray arr = (JSONArray) value;
                if (arr.length() > 0 && arr.get(0) instanceof JSONObject) {
                    javaType = "List<" + key + "Dto>";
                    generateJavaFile(key + "Dto", arr.getJSONObject(0));
                } else {
                    javaType = "List<String>";
                }
            } else if (value instanceof Number) {
                javaType = "BigDecimal";
            }

            sb.append("    @SerializedName(\"").append(key).append("\")\n");
            sb.append("    private ").append(javaType).append(" ").append(key).append(";\n\n");
        }
        sb.append("}\n");

        Path path = Paths.get(SAVE_PATH + className + ".java");
        Files.createDirectories(path.getParent());
        Files.writeString(path, sb.toString());
        System.out.println("파일 생성됨: " + path.toAbsolutePath());
    }

    private static boolean isCommonField(String key) {
        return List.of("SYS_INF", "TR_INF", "MSG_INF", "root_info").contains(key);
    }
}