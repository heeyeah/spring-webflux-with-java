package shb.ms.webflux;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Movie {

    private long id;
    private String title;
    private String category;
}
