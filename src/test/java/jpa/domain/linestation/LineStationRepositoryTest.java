package jpa.domain.linestation;

import jpa.domain.line.Line;
import jpa.domain.line.LineRepository;
import jpa.domain.station.Station;
import jpa.domain.station.StationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@DataJpaTest
public class LineStationRepositoryTest {
    @Autowired
    LineRepository lines;

    @Autowired
    StationRepository stations;

    @Autowired
    LineStationRepository lineStations;

    @Test
    void save() {
        Line line = lines.save(new Line("2호선"));
        Station jamsilStation = stations.save(new Station("잠실역"));
        Station samsungStation = stations.save(new Station("삼성역"));

        LineStation lineStation = lineStations.save(new LineStation(line, jamsilStation));
        LineStation lineStation2 = lineStations.save(new LineStation(line, samsungStation));
    }

    @DisplayName("필수값 누락")
    @Test
    void validate() {
        Line line = lines.save(new Line("2호선"));

        assertThatIllegalArgumentException().isThrownBy(() -> {
            LineStation lineStation = lineStations.save(new LineStation(line, null));
        })
                .withMessageContaining("필수값 누락입니다.");

    }
}
