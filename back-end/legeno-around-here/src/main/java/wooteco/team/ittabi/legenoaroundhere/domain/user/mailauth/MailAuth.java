package wooteco.team.ittabi.legenoaroundhere.domain.user.mailauth;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wooteco.team.ittabi.legenoaroundhere.domain.BaseEntity;
import wooteco.team.ittabi.legenoaroundhere.domain.user.Email;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class MailAuth extends BaseEntity {

    @Embedded
    private Email email;

    @Embedded
    private AuthNumber authNumber;

    @Builder
    public MailAuth(String email, int authNumber) {
        this.email = new Email(email);
        this.authNumber = new AuthNumber(authNumber);
    }

    public boolean isSameAuthNumber(int authNumber) {
        return this.authNumber.isSame(authNumber);
    }
}
