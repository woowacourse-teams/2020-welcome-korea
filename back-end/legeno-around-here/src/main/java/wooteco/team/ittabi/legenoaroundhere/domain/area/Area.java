package wooteco.team.ittabi.legenoaroundhere.domain.area;

import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import wooteco.team.ittabi.legenoaroundhere.domain.BaseEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Area extends BaseEntity {

    String fullName;
    String firstDepthName;
    String secondDepthName;
    String thirdDepthName;
    String fourthDepthName;
    byte used;
}
