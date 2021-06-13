# Transforming
Have mutable views of other collections which are mapped

```java
import java.util.HashSet;
import java.util.Set;

public class Example {
    public static void main(String[] args) {
        Set<Integer> set = new HashSet<>();
        Set<String> strings = new TransformingSet<>(set, IntegerStringMapper.INSTANCE);
        set.add(14);

        for (String s : strings) {
            System.out.println(s); // 14
        }
    }
}
```

# License
Apache 2.0
