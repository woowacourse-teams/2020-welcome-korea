package wooteco.team.ittabi.legenoaroundhere.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.team.ittabi.legenoaroundhere.exception.UserInputException;

public class PostTest {

    @DisplayName("길이 검증 - 예외 발생")
    @Test
    void validateLength_OverLength_ThrownException() {
        String overLengthInput = "aaaaaaaaaaaaaaaaaaaaa";
        assertThatThrownBy(() -> new Post(overLengthInput))
            .isInstanceOf(UserInputException.class);
    }

    @DisplayName("같은 상태인지 확인")
    @Test
    void isSameState_SameState_True() {
        Post post = new Post("Hello!");
        post.setState(State.DELETED);

        assertThat(post.isSameState(State.DELETED)).isTrue();
    }

    @DisplayName("다른 상태인지 확인")
    @Test
    void isNotSameState_DifferentState_True() {
        Post post = new Post("Hello!");
        post.setState(State.DELETED);

        assertThat(post.isNotSameState(State.PUBLISHED)).isTrue();
    }
}
