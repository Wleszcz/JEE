package company.serialization.component;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.Test;

import java.io.Serial;
import java.io.Serializable;

import static org.assertj.core.api.Assertions.assertThat;

class CloningUtilityTest {

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @EqualsAndHashCode
    @ToString
    @SuppressWarnings("RedundantThrows")
    private static final class Model implements Serializable {

        @Serial
        private static final long serialVersionUID = 1234567L;

        private String text;

        private int number;

    }

    @Test
    void clone_serializableStringObject_deepCopy() {
        CloningUtility instance = new CloningUtility();

        String value = "string object";
        String actual = instance.clone(value);

        assertThat(actual).isEqualTo(value);
        assertThat(actual).isNotSameAs(value);
    }

    @Test
    void clone_serializableObject_deepCopy() {
        CloningUtility instance = new CloningUtility();

        Model expected = Model.builder()
                .text("string value")
                .number(1)
                .build();
        Model actual = instance.clone(expected);

        assertThat(actual).isEqualTo(expected);
        assertThat(actual).isNotSameAs(expected);
    }

}
