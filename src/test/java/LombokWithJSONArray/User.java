package LombokWithJSONArray;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private int page;
    private int per_page;
    private int total;
    private int total_pages;
    private List<UserData> data;
    private Support support;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserData{
        private  int id;
        private String email;
        private String first_name;
        private String last_name;
        private String avatar;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Support{
        private String url;
        private String text;
    }

}
