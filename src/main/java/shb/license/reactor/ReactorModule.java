package shb.license.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ReactorModule {

    public static void fluxSample() {

        Flux<String> seq1 = Flux.just("hello", "world", ":)");

        List<String> iterable = Arrays.asList("foo", "bar", "foobar");
        Flux<String> seq2 = Flux.fromIterable(iterable);

        seq1.subscribe(); // Subscribe and trigger the sequence.
        seq2.subscribe(i -> System.out.println(i)); //	Do something with each produced value.


        Flux<Integer> ints = Flux.range(1, 4)
                .map(i -> {
                    if (i <= 3) return i;
                    throw new RuntimeException("Got to 4");
                });
        ints.subscribe(i -> System.out.println(i),
                error -> System.err.println("Error: " + error)); //Deal with values but also react to an error.

        Flux<Integer> ints2 = Flux.range(1, 4);
        ints2.subscribe(i -> System.out.println(i),
                error -> System.err.println("Error " + error),
                () -> System.out.println("Done")); //	Deal with values and errors but also run some code when the sequence successfully completes.
    }

    public static void monoSample() {
        List<String> emptyList = new ArrayList<>();

        Mono<String> noData = Mono.empty();
        noData.subscribe(emptyList::add);
        System.out.println("noData length : " + emptyList.size());

        Mono<String> data = Mono.just("Cloud");
        data.log().subscribe(emptyList::add);
        System.out.println("data length : " + emptyList.size());
        System.out.println("data value : " + ((emptyList.size() > 0) ? emptyList.get(0) : ""));
    }

    public static void webfluxSample() {

    }


    public static void main(String[] args) {


        fluxSample();

//        monoSample();

        System.out.println("* webfluxSample =>");
        webfluxSample();
    }
}
