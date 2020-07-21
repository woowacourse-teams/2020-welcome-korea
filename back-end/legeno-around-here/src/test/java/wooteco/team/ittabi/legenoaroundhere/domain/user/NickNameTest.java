package wooteco.team.ittabi.legenoaroundhere.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class NickNameTest {

    private static final String TEST_NAME = "testName";

    @Test
    @DisplayName("생성자 테스트")
    void constructor() {
        assertThat(new NickName(TEST_NAME)).isInstanceOf(NickName.class);
    }

    @Test
    @DisplayName("생성자 테스트 - 닉네임이 null 일 때")
    void constructor_IfNull_ThrowException() {
        assertThatThrownBy(() -> new NickName(null))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("생성자 테스트 - 닉네임이 비어있을 때")
    void constructor_IfBlank_ThrowException() {
        assertThatThrownBy(() -> new NickName(""))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("생성자 테스트 - 닉네임에 공백이 포함되었을 때")
    @ValueSource(strings = {" ", "a ", " a", "a a"})
    void constructor_IfExistSpace_ThrowException(String input) {
        assertThatThrownBy(() -> new NickName(input))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("생성자 테스트 - 닉네임이 10글자를 초과했을 때")
    void constructor_IfLengthIsMoreThanMaximum_ThrowException() {
        assertThatThrownBy(() -> new NickName("이글자는열한글자입니다"))
            .isInstanceOf(IllegalArgumentException.class);
    }
}