package domain.position;

import static java.util.stream.Collectors.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

@DisplayName("Position은 ")
class PositionTest {

    @Test
    @DisplayName("캐싱된다.")
    void getCachedPositionTest() {
        // given
        Position position1 = Position.of(2, 2);
        Position position2 = Position.of(2, 2);

        // expect
        assertThat(position1).isSameAs(position2);
    }

    @Test
    @DisplayName("캐싱된 Position의 개수는 64개이다.")
    void checkNumberOfPositionTest() {
        // given
        List<Integer> rows = List.of(1, 2, 3, 4, 5, 6, 7, 8);
        List<Integer> columns = List.of(1, 2, 3, 4, 5, 6, 7, 8);

        List<Position> expectedPositions = rows.stream()
                .flatMap(row -> columns.stream()
                        .map(column -> Position.of(row, column)))
                .collect(toList());

        // when
        List<Position> setPosition = Position.getAllPosition();

        // then
        assertThat(setPosition)
                .containsAll(expectedPositions)
                .hasSize(64);
    }

    @Test
    @DisplayName("위쪽에 있는 Position을 반환할 수 있다.")
    void moveUpTest() {
        // given
        Position position = Position.of(4, 4);

        // when
        Position newPosition = position.moveUp();

        // then
        assertThat(newPosition).isSameAs(Position.of(5, 4));
    }

    @Test
    @DisplayName("아래쪽에 있는 Position을 반환할 수 있다.")
    void moveDownTest() {
        // given
        Position position = Position.of(4, 4);

        // when
        Position newPosition = position.moveDown();

        // then
        assertThat(newPosition).isSameAs(Position.of(3, 4));
    }

    @Test
    @DisplayName("왼쪽에 있는 Position을 반환할 수 있다.")
    void moveLeftTest() {
        // given
        Position position = Position.of(4, 4);

        // when
        Position newPosition = position.moveLeft();

        // then
        assertThat(newPosition).isSameAs(Position.of(4, 3));
    }

    @Test
    @DisplayName("오른쪽에 있는 Position을 반환할 수 있다.")
    void moveRightTest() {
        // given
        Position position = Position.of(4, 4);

        // when
        Position newPosition = position.moveRight();

        // then
        assertThat(newPosition).isSameAs(Position.of(4, 5));
    }

    @Test
    @DisplayName("왼쪽위 대각선에 있는 Position을 반환할 수 있다.")
    void moveUpLeftTest() {
        // given
        Position position = Position.of(4, 4);

        // when
        Position newPosition = position.moveUpLeft();

        // then
        assertThat(newPosition).isSameAs(Position.of(5, 3));
    }

    @Test
    @DisplayName("오른쪽위 대각선에 있는 Position을 반환할 수 있다.")
    void moveUpRightTest() {
        // given
        Position position = Position.of(4, 4);

        // when
        Position newPosition = position.moveUpRight();

        // then
        assertThat(newPosition).isSameAs(Position.of(5, 5));
    }

    @Test
    @DisplayName("왼쪽 아래 대각선에 있는 Position을 반환할 수 있다.")
    void moveDownLeftTest() {
        // given
        Position position = Position.of(4, 4);

        // when
        Position newPosition = position.moveDownLeft();

        // then
        assertThat(newPosition).isSameAs(Position.of(3, 3));
    }

    @Test
    @DisplayName("오른쪽 아래 대각선에 있는 Position을 반환할 수 있다.")
    void moveDownRightTest() {
        // given
        Position position = Position.of(4, 4);

        // when
        Position newPosition = position.moveDownRight();

        // then
        assertThat(newPosition).isSameAs(Position.of(3, 5));
    }

    @ParameterizedTest
    @MethodSource("getPathToTestCase")
    @DisplayName("목적지 Position까지의 경로를 반환할 수 있다.")
    void getPathToTest(Position start, Position end, List<Position> path) {
        assertThat(start.getPathTo(end)).containsAll(path);
    }

    @Test
    @DisplayName("black pawn의 초기 위치이면 true를 반환한다.")
    void isBlackPawnInitialRowTest_True() {
        // given
        Position start = Position.of(7,5);

        // when
        boolean result = start.isBlackPawnInitialRow();

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("black pawn의 초기 위치가 아니면 false를 반환한다.")
    void isBlackPawnInitialRowTest_False() {
        // given
        Position start = Position.of(3,5);

        // when
        boolean result = start.isBlackPawnInitialRow();

        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("white pawn의 초기 위치이면 true를 반환한다.")
    void isWhitePawnInitialRowTest_True() {
        // given
        Position start = Position.of(2,5);

        // when
        boolean result = start.isWhitePawnInitialRow();

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("white pawn의 초기 위치가 아니면 false를 반환한다.")
    void isWhitePawnInitialRowTest_False() {
        // given
        Position start = Position.of(3,5);

        // when
        boolean result = start.isWhitePawnInitialRow();

        // then
        assertThat(result).isFalse();
    }

    static Stream<Arguments> getPathToTestCase() {
        return Stream.of(
                Arguments.arguments(Position.of(3,3), Position.of(5, 3), List.of(Position.of(4, 3), Position.of(5, 3))),
                Arguments.arguments(Position.of(3,3), Position.of(5, 5), List.of(Position.of(4, 4), Position.of(5, 5))),
                Arguments.arguments(Position.of(3,3), Position.of(3, 5), List.of(Position.of(3, 4), Position.of(3, 5))),
                Arguments.arguments(Position.of(3,3), Position.of(1, 5), List.of(Position.of(2, 4), Position.of(1, 5))),
                Arguments.arguments(Position.of(3,3), Position.of(1, 3), List.of(Position.of(2, 3), Position.of(1, 3))),
                Arguments.arguments(Position.of(3,3), Position.of(1, 1), List.of(Position.of(2, 2), Position.of(1, 1))),
                Arguments.arguments(Position.of(3,3), Position.of(3, 1), List.of(Position.of(3, 2), Position.of(3, 1))),
                Arguments.arguments(Position.of(3,3), Position.of(5, 1), List.of(Position.of(4, 2), Position.of(5, 1)))
        );
    }
}
